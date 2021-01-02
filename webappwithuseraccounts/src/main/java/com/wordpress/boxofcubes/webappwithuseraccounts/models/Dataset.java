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
}
