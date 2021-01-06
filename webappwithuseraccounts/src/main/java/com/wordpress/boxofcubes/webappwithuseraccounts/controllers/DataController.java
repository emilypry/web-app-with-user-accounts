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
    @Autowired
    private DatasetRepository datasetRepository;

    @GetMapping("upload")
    public String showUpload(@RequestParam(required=false) String user_id){
        return "data/upload";
    }

    @PostMapping("upload")
    public String processUpload(@RequestParam(required=false) String user_id,
        @RequestParam MultipartFile xFile, 
                                @RequestParam MultipartFile yFile, 
                                @RequestParam String xEntry, String yEntry, Model model){

        if(xFile.isEmpty() && yFile.isEmpty() && xEntry.isEmpty() && yEntry.isEmpty()){
            System.out.println("No data entered");
            model.addAttribute("noDataError", "No data entered");
        }else if((!xFile.isEmpty() || !yFile.isEmpty()) && (!xEntry.isEmpty() || !yEntry.isEmpty())){
            System.out.println("Data entry types mixed");
            model.addAttribute("dataMixError", "Please either upload or enter data");
        }else if(!xFile.isEmpty() && yFile.isEmpty()){
            System.out.println("Missing Y file");
            model.addAttribute("yFileError", "Y file is missing");
        }else if(!yFile.isEmpty() && xFile.isEmpty()){
            System.out.println("Missing X file");
            model.addAttribute("xFileError", "X file is missing");
        }else if(!xEntry.isEmpty() && yEntry.isEmpty()){
            System.out.println("Missing Y entry");
            model.addAttribute("yEntryError", "Y entry is missing");
        }else if(!yEntry.isEmpty() && xEntry.isEmpty()){
            System.out.println("Missing X entry");
            model.addAttribute("xEntryError", "X entry is missing");
        }
        

        return "data/upload";
        //return "redirect:/data/upload";




        /*
        File newXfile = new File(xFile.getOriginalFilename());
        File newYfile = new File(yFile.getOriginalFilename());

        double[] xVals;
        double[] yVals;
        try {
            xFile.transferTo(newXfile);
            xVals = Dataset.convertToNums(newXfile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Couldn't find X file");
            return "redirect:/data/upload";
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error with X file");
            return "redirect:/data/upload";
        }

        try {
            yFile.transferTo(newYfile);
            yVals = Dataset.convertToNums(newYfile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Couldn't find Y file");
            return "redirect:/data/upload";
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error with Y file");
            return "redirect:/data/upload";
        }

        if(xVals.length != yVals.length){
            System.out.println("Number of X and Y values must be identical");
            return "redirect:/data/upload";
        }

        Dataset dataset = new Dataset(xVals, yVals);
        datasetRepository.save(dataset);

        if(user_id != null){
            return "redirect:/data/graph?user_id="+user_id;
        }

        return "redirect:/data/graph";*/



    }

    @GetMapping("graph")
    public String showGraph(Model model){

        return "data/graph";
    }
}
