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
import java.util.Optional;
import java.util.Scanner;

import com.wordpress.boxofcubes.webappwithuseraccounts.data.DatasetRepository;
import com.wordpress.boxofcubes.webappwithuseraccounts.data.FileSaverRepository;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.Dataset;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.FileSaver;

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
    //@Autowired
    //private DatasetRepository datasetRepository;

    @GetMapping("upload")
    public String showUpload(){
        return "data/upload";
    }

    @PostMapping("upload")
    public String processUpload(@RequestParam MultipartFile xFile, Model model){
        File file = new File(xFile.getOriginalFilename());
        try {
            xFile.transferTo(file);
            double[] numbers = Dataset.convertToNums(file);

            model.addAttribute("numbers", numbers);

            return "redirect:/data/graph";

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } 
        return "redirect:/data/upload";


        



        //byte[] theBytes = xFile.getBytes();
        //InputStream inputStream =  new BufferedInputStream(xFile.getInputStream());
        //return "ok";
        /*BufferedReader reader;
        
        try {
            InputStream stream = xFile.getInputStream(); 
            reader = new BufferedReader(new InputStreamReader(stream));

            String data = "";
            while(true){
                String line = reader.readLine();
                if(line != null){
                    data += line + " ";
                }else{
                    break;
                }
            }
            return data;
        }catch(IOException e){
            e.printStackTrace();
        }
        return "error!";*/
    }

    @GetMapping("graph")
    public String showGraph(){
        return "data/graph";
    }
}
