package com.wordpress.boxofcubes.webappwithuseraccounts.models;

import java.util.ArrayList;

public class User {
    public int id;
    private String username;
    private String password; //change later
    private ArrayList<Data> data;

    public User(){}
    public User(int id, String username, String password, ArrayList<Data> data){
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
    public ArrayList<Data> getData(){
        return data;
    }
    public void setData(ArrayList<Data> data){
        this.data = data;
    }
}