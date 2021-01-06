package com.wordpress.boxofcubes.webappwithuseraccounts.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.wordpress.boxofcubes.webappwithuseraccounts.models.Dataset;

import java.io.FileNotFoundException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("data")
public class DataController {
    @GetMapping("upload")
    public String showUpload(){
        return "data/upload";
    }

    @PostMapping("upload")
    @ResponseBody
    public String processUpload(@RequestParam MultipartFile xFile){
        File file = new File(xFile.getOriginalFilename());
        try {
            xFile.transferTo(file);
            double[] nums = Dataset.convertToNums(file);


        }catch (IOException e) {
            e.printStackTrace();
        } 

        return "ok";

        /*double[] dubs = new double[nums.size()];
        for(int i=0; i<nums.size(); i++){
            dubs[i] = nums.get(i);
            System.out.println(dubs[i]);
        }

        //byte[] theBytes = xFile.getBytes();
        //InputStream inputStream =  new BufferedInputStream(xFile.getInputStream());
        return "ok";
        BufferedReader reader;
        
        try {
            InputStream is = xFile.getInputStream(); //exception is thrown here
            reader = new BufferedReader(new InputStreamReader(is));

            String data = "";
            while(true){
                String line = reader.readLine();
                if(line != null){
                    data += line + "\t";
                }else{
                    break;
                }
                
            }
            
            return data;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return "error";*/
    }
}
