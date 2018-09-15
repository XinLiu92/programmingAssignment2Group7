package main;

import edu.unh.cs.treccar_v2.Data;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadData {

    public static void main(String args[]){
//        if (args.length < 1){
//            System.out.println("command line argument : FileDirectory");
//        }

//        String filePath = args[0];


        System.out.println("reading data");



    }


    public static List<Paragraph> getParagraphList() {
        List<Paragraph> paragraphsList = new ArrayList<>();
        String fileDir = "./test200-train/train.pages.cbor-paragraphs.cbor";
        File file = new File(fileDir);

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (stream == null){
            System.out.println("file input stream created failed");
        }

        try {
            for (Data.Paragraph parag : DeserializeData.iterableParagraphs(stream)){
                Paragraph p = new Paragraph(parag.getParaId(),parag.getTextOnly());
                paragraphsList.add(p);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return  paragraphsList;
    }

    public static List<Page> getPageList(){
        List<Page> pageList = new ArrayList<>();

        String fileDir = "./test200-train/train.pages.cbor-outlines.cbor";

        File file = new File(fileDir);

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (stream == null){
            System.out.println("file input stream created failed");
        }

        try {
            for (Data.Page page : DeserializeData.iterableAnnotations(stream)){
                Page p = new Page(page.getPageId(),page.getPageName());
                pageList.add(p);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return  pageList;
    }

}
