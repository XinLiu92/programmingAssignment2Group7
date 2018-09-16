package main;

import co.nstant.in.cbor.CborException;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class PrecisionAtR {

    public static void main(String args[]) throws ParseException, IOException, CborException {



        String defaulScoreInput = args[0];

        boolean defaultScore = Boolean.valueOf(defaulScoreInput);


        HashMap<String, List<Rank>> rankMap = Main.rankResultMap(defaultScore);

        TreeMap<String,List<String>> relevantMap = ReadData.getRelevant();



        HashMap<String, Double> precisionAtR = new HashMap<>();
        System.out.println("QueryId     |       PrecisionAtR");

        for (String queryId : precisionAtR.keySet()){
            //tp
            if (rankMap.get(queryId) != null && relevantMap.get(queryId) != null){
                double pR = calculatePR(rankMap.get(queryId),relevantMap.get(queryId));

                precisionAtR.put(queryId,pR);
            }else {
                System.out.println("no such ranked or relevant documents: " + queryId);
            }
        }

    }

    public static double calculatePR(List<Rank> rankList,List<String> relevantDocList ){
        int tpNum = 0;
        double precision = 0;

        int relevantNum = relevantDocList.size();

        if (relevantNum > 0){
            List<Rank> firstRDoc = rankList.subList(0,Math.min(relevantNum,rankList.size()));

            for (Rank rank : firstRDoc){
                if (relevantDocList.contains(rank.getParagId())){
                    tpNum++;
                }
            }

            precision = (double) tpNum/relevantNum;
        }

        return  precision;
    }

    public static void writeResult(HashMap<String, Double> resultMap,boolean useDefaultScore){
        if (resultMap.isEmpty()){
            System.out.println("rank map is empty");
            return;
        }

        String output = "precisionAtR";

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

            for (String queryId: resultMap.keySet()){

                String line = String.format("%-40s %20s \r\n", queryId, resultMap.get(queryId));
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
