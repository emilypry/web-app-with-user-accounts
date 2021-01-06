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
                                @RequestParam MultipartFile yFile, Model model){
        
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
        
        return "redirect:/data/graph";



        /*try {
            xFile.transferTo(newXfile);
            double[] numbers = Dataset.convertToNums(newXfile);
            model.addAttribute("numbers", numbers);

            return "redirect:/data/graph";

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } */
        //return "redirect:/data/upload";
    }

    @GetMapping("graph")
    public String showGraph(Model model){
        return "data/graph";
    }
}
