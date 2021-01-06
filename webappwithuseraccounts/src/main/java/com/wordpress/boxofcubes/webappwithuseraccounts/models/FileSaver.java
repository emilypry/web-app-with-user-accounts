package com.wordpress.boxofcubes.webappwithuseraccounts.models;

import java.io.File;

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

}
