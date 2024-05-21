package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {

    public static void main(String[] args) {
        PriorityBlockingQueue<Product> products=new PriorityBlockingQueue<>(10,new ProductComparator());
        try {
            ProductThread t1=new ProductThread("CKThread","calvin_klein.txt",products);
            ProductThread t2=new ProductThread("GuessThread","guess.txt",products);
            ProductThread t3=new ProductThread("TrussardiThread","trussardi.txt", products);
            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();


        } catch (Exception e){
            System.out.println("There was an error while reading the data.");
            System.exit(1);
        }
        ArrayList<Product> polledElements=new ArrayList<>();
        products.drainTo(polledElements);
        PrintWriter pw=null;
        File file=new File("Output.txt");
        try{
            pw=new PrintWriter(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        for(int i=0;i<10;i++){
            pw.println(polledElements.get(i).toString());
        }
        pw.close();
    }
}
