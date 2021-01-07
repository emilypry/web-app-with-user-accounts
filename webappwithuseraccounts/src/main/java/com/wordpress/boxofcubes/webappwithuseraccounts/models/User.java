package com.wordpress.boxofcubes.webappwithuseraccounts.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    @NotNull(message="Username is required")
    @Size(min=5, max=20, message="Username must be 5-20 characters long")
    private String username;

    @NotNull(message="Password is required")
    @Size(min=7, max=20, message="Password must be 7-20 characters long")
    private String password; 

    @OneToMany(mappedBy = "owner")
    private final List<Dataset> data = new ArrayList<>();

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
    public List<Dataset> getData(){
        return data;
    }

}