package com.wordpress.boxofcubes.webappwithuseraccounts.models;

public class User{
    public int id;
    private String username;
    private String password; //change later

    public User(){}
    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
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
}