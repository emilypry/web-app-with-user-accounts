package com.wordpress.boxofcubes.webappwithuseraccounts.models;

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

    public static double[] convertToNums(String data){
        System.out.println("called");
        String[] dataPieces = data.split("\\s");

        double[] nums = new double[dataPieces.length];
        for(int i=0; i<dataPieces.length; i++){
            System.out.print(dataPieces[i]+" ");
            nums[i] = Double.valueOf(dataPieces[i]);
            System.out.println(nums[i]);
        }

        return nums;
    }

    public static void main(String[] args){
        String data = "1   2 3  4 5 ";
        convertToNums(data);
    }
}
