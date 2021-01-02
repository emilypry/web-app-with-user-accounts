package com.wordpress.boxofcubes.webappwithuseraccounts.models;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    @Size(min=7, max=20)
    private String username;
    private String password; //change later
    private ArrayList<Dataset> data;

    public User(){}
    public User(int id, String username, String password, ArrayList<Dataset> data){
        this.id = id;
        this.username = username;
        this.password = password;
        this.data = data;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public ArrayList<Dataset> getData(){
        return data;
    }
    public void setData(ArrayList<Dataset> data){
        this.data = data;
    }
}