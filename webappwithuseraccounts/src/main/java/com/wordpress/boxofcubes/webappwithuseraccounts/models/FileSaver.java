package com.wordpress.boxofcubes.webappwithuseraccounts.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FileSaver {
    @Id
    @GeneratedValue
    private int id;

    private File file;

    public FileSaver(){}
    public FileSaver(int id, File file){
        this.id = id;
        this.file = file;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public File getFile(){
        return file;
    }
    public void setFile(File file){
        this.file = file;
    }

    public double[] convertToNums(){
        ArrayList<Double> numbers = new ArrayList<>();
        Scanner scan = null;
        try{
            scan = new Scanner(file);
            scan.useDelimiter("\s*|,");
            while(scan.hasNext()){
                numbers.add(scan.nextDouble());
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(InputMismatchException e){
            e.printStackTrace();
        }finally{
            if(null != scan){
                scan.close();
            }
        }

        double[] list = new double[numbers.size()];
        for(int i=0; i<numbers.size(); i++){
            list[i] = numbers.get(i);
            System.out.println(list[i]);
        }

        return list;        
    }

}
