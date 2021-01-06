package com.wordpress.boxofcubes.webappwithuseraccounts.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dataset {
    @Id
    @GeneratedValue
    private int id;
    private double[] x;
    private double[] y;

    public Dataset(){}
    public Dataset(int id, double[] x, double[] y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public double[] getX(){
        return x;
    }
    public void setX(double[] x){
        this.x = x;
    }
    public double[] getY(){
        return y;
    }
    public void setY(double[] y){
        this.y = y;
    }

    public static double[] convertToNums(File file){
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

    public static void main(String[] args){
        String data = "1   2 3  4 5 ";
        convertToNums(data);
    }
}
