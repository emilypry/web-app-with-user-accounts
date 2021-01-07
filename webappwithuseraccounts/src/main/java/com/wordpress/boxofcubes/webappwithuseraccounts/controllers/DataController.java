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
import com.wordpress.boxofcubes.webappwithuseraccounts.data.UserRepository;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.Dataset;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.User;

import java.io.FileNotFoundException;

import org.apache.commons.io.FileUtils;
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

    @Autowired
    private UserRepository userRepository; //added

    @GetMapping("upload")
    public String showUpload(@RequestParam(required=false) Integer user_id){
        return "data/upload";
    }

    @PostMapping("upload")
    public String processUpload(@RequestParam(required=false) Integer user_id,
                                @RequestParam MultipartFile xFile, 
                                @RequestParam MultipartFile yFile, 
                                @RequestParam String xEntry, String yEntry, Model model){

        boolean errors = false;

        if(xFile.isEmpty() && yFile.isEmpty() && xEntry.isEmpty() && yEntry.isEmpty()){
            model.addAttribute("generalError", "No data entered");
            errors = true;
        }else if((!xFile.isEmpty() || !yFile.isEmpty()) && (!xEntry.isEmpty() || !yEntry.isEmpty())){
            model.addAttribute("generalError", "Please either upload or enter data");
            errors = true;
        }else if(!xFile.isEmpty() && yFile.isEmpty()){
            model.addAttribute("yFileError", "Y file is missing");
            errors = true;
        }else if(!yFile.isEmpty() && xFile.isEmpty()){
            model.addAttribute("xFileError", "X file is missing");
            errors = true;
        }else if(!xEntry.isEmpty() && yEntry.isEmpty()){
            model.addAttribute("yEntryError", "Y entry is missing");
            errors = true;
        }else if(!yEntry.isEmpty() && xEntry.isEmpty()){
            model.addAttribute("xEntryError", "X entry is missing");
            errors = true;
        }

        File newXfile; 
        File newYfile; 
        double[] xVals = null;
        double[] yVals = null;

        if(!xFile.isEmpty()){
            try{ 
                newXfile = new File(xFile.getOriginalFilename());
                xFile.transferTo(newXfile);
                xVals = Dataset.convertToNums(newXfile);
            }catch(FileNotFoundException e){
                model.addAttribute("xFileError", "Can't find X file");
                errors = true;
            }catch(InputMismatchException e){
                model.addAttribute("xFileError", "X file contains non-numbers");
                errors = true;
            }catch(IOException e){
                model.addAttribute("xFileError", "Error with X file");
                errors = true;
            }
            try{ 
                newYfile = new File(xFile.getOriginalFilename());
                yFile.transferTo(newYfile);
                yVals = Dataset.convertToNums(newYfile);
            }catch(FileNotFoundException e){
                model.addAttribute("yFileError", "Can't find Y file");
                errors = true;
            }catch(InputMismatchException e){
                model.addAttribute("yFileError", "Y file contains non-numbers");
                errors = true;
            }catch(IOException e){
                model.addAttribute("yFileError", "Error with Y file");
                errors = true;
            }
        }else if(!xEntry.isEmpty()){
            try{
                newXfile = new File("file");
                FileUtils.writeStringToFile(newXfile, xEntry);
                xVals = Dataset.convertToNums(newXfile);
            }catch(FileNotFoundException e){
                model.addAttribute("xEntryError", "Error with X entry");
                errors = true;
            }catch(InputMismatchException e){
                model.addAttribute("xEntryError", "X entry contains non-numbers");
                errors = true;
            }catch(IOException e){
                model.addAttribute("xEntryError", "Error with X entry");
                errors = true;
            }
            try{
                newYfile = new File("file");
                FileUtils.writeStringToFile(newYfile, yEntry);
                yVals = Dataset.convertToNums(newYfile);
            }catch(FileNotFoundException e){
                model.addAttribute("yEntryError", "Error with Y entry");
                errors = true;
            }catch(InputMismatchException e){
                model.addAttribute("yEntryError", "Y entry contains non-numbers");
                errors = true;
            }catch(IOException e){
                model.addAttribute("yEntryError", "Error with Y entry");
                errors = true;
            }  
        }

        if(errors == false && xVals.length != yVals.length){
            model.addAttribute("generalError", "Number of X and Y values must be identical");
            errors = true;
        }

        if(errors == true){
            return "data/upload";
        }else{
            
            if(user_id == null){
                Dataset dataset = new Dataset(xVals, yVals); // modified
                datasetRepository.save(dataset);
                return "redirect:/data/graph";
            }else{
                Optional<User> user = userRepository.findById(user_id);
                if(user.isPresent()){
                    Dataset dataset = new Dataset(xVals, yVals, user.get()); // modified, moved
                    datasetRepository.save(dataset);
                }
                return "redirect:/data/graph?user_id="+user_id;
            }
        }



        
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
