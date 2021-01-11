package com.wordpress.boxofcubes.webappwithuseraccounts.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.io.FileUtils;

@Entity
public class Dataset {
    @Id
    @GeneratedValue
    private int id;

    private double[] x;
    private double[] y;

    @ManyToOne 
    private User owner; 

    public Dataset(){}
    public Dataset(double[] x, double[] y){ 
        this.x = x;
        this.y = y;
    }
    public Dataset(double[] x, double[] y, User owner){ 
        this.x = x;
        this.y = y;
        this.owner = owner;
    }
    public Dataset(int id, double[] x, double[] y, User owner){ 
        this.id = id;
        this.x = x;
        this.y = y;
        this.owner = owner;
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
    public User getOwner(){
        return owner;
    }
    public void setOwner(User owner){
        this.owner = owner;
    }


    public static double[] convertToNums(File file) throws FileNotFoundException, InputMismatchException{
        ArrayList<Double> numbers = new ArrayList<>();
    
        Scanner scan = new Scanner(file);
        while(scan.hasNext()){
            numbers.add(scan.nextDouble());
        }
        scan.close();

        double[] list = new double[numbers.size()];
        for(int i=0; i<numbers.size(); i++){
            list[i] = numbers.get(i);
        }

        return list;
    }

}
