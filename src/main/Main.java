package main;

import co.nstant.in.cbor.CborException;
import edu.unh.cs.treccar_v2.Data;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import javax.print.Doc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException, ParseException, CborException {
        System.setProperty("file.encoding", "UTF-8");
        String methdName = "";
        //read data

        //
        ///Users/xinliu/Documents/UNH/18Fall/cs853/index /Users/xinliu/Documents/UNH/18Fall/cs853/test200/test200-train/train.pages.cbor-paragraphs.cbor
        String defualtScore = args[0];
        String indexPath = args[1];
        boolean useDefaultScore = Boolean.valueOf(defualtScore);
        if (useDefaultScore){
            methdName = "default";
            System.out.println("using lucene default score function");
        }else{
            methdName = "custome";
            System.out.println("using lucene customed score function");
        }



        List<Paragraph> paragraphsList = ReadData.getParagraphList();

        List<Page> pageList = ReadData.getPageList();

        List<Rank> rankList = getRankList(paragraphsList,pageList,useDefaultScore,methdName,indexPath);

        writeResult(rankList,useDefaultScore);

    }


    public static List<Rank> getRankList(List<Paragraph> paragraphList,List<Page> pageList, boolean useDefaultScore, String methodName,String indexPath) throws IOException, ParseException, CborException {
        List<Rank> rankList = new ArrayList<>();
        Indexer indexer = new Indexer(useDefaultScore,indexPath);

        indexer.rebuildIndexes(paragraphList);
       for (Page page : pageList){
           System.out.println("searching for query: "+ page.getPageName());

           String query = page.getPageName();

           SearchEngine se = new SearchEngine(useDefaultScore,indexPath);
           TopDocs topDocs = se.performSearch(query, 100);
           System.out.println("Result found: "+topDocs.totalHits);

            ScoreDoc[] hits = topDocs.scoreDocs;

            for (int i = 0; i < hits.length;i++){
                Document document = se.getDocument(hits[i].doc);

                Rank rank = new Rank();
                rank.setQueryId(page.getPageId());
                rank.setParagId(document.get("id"));
                rank.setRank(i+1);
                rank.setScore(hits[i].score);
                String methodTeamName = "Group7" + "-"+methodName;

                rank.setMethodTeamName(methodTeamName);
                rankList.add(rank);
            }


       }
        System.out.println("search done!    "+ "get rank list with size: " +rankList.size());
        return rankList;
    }


    public static void writeResult(List<Rank> rankList,boolean useDefaultScore){
        if (rankList.isEmpty()){
            System.out.println("rank list is empty");
            return;
        }

        String output = "rankResult";

        if (useDefaultScore){
            output = output + "-defaultScoreFunc.txt";
        }else{
            output = output + "-customScoreFunc.txt";
        }

        String path = "./"+output;

        BufferedWriter bufferWriter = null;
        FileWriter fileWriter = null;

        try {

            fileWriter = new FileWriter(path);
            bufferWriter = new BufferedWriter(fileWriter);

            for (Rank rank : rankList){
                String line = rank.getGueryId() + " "+"Q0"+" "+rank.getParagId()+" "+rank.getRank()+" "
                        +rank.getScore()+" "+rank.getMethodTeamName();
                bufferWriter.write(line);

                bufferWriter.newLine();
            }

            System.out.println("output file wrote to file: "+path);
        }catch (IOException e){
            e.printStackTrace();
        }finally {

            try {
                if (bufferWriter != null){
                    bufferWriter.close();
                }
                if (fileWriter!=null){
                    fileWriter.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }


    }
}
