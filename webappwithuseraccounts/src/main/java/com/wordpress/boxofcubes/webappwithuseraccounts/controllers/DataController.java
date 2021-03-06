package com.wordpress.boxofcubes.webappwithuseraccounts.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.wordpress.boxofcubes.webappwithuseraccounts.data.DatasetRepository;
import com.wordpress.boxofcubes.webappwithuseraccounts.data.UserRepository;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.Dataset;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.User;


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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("data")
public class DataController {
    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private UserRepository userRepository; 

    @GetMapping("upload")
    public String showUpload(@RequestParam(required=false) Integer userId, Model model){
        model.addAttribute("userId", userId);
        return "data/upload";
    }

    @PostMapping("upload")
    public String processUpload(@RequestParam(required=false) Integer userId,
                                @RequestParam MultipartFile xFile, 
                                @RequestParam MultipartFile yFile, 
                                @RequestParam String xEntry, String yEntry, Model model,
                                HttpServletRequest request){

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
            if(userId == null){
                // Save the dataset to the repository
                Dataset dataset = new Dataset(xVals, yVals); 
                datasetRepository.save(dataset);

                // Create a unique ID for this Dataset object
                String dataObjectId = UUID.randomUUID().toString();
                request.getSession().setAttribute(dataObjectId, dataset);

                // Go to the page for ChartServlet
                return "redirect:/data/graph?datasetId="+dataset.getId()+"&dataObjectId="+dataObjectId;
            }else{
                Optional<User> user = userRepository.findById(userId);
                if(user.isPresent()){
                    // Save the dataset to the repository
                    Dataset dataset = new Dataset(xVals, yVals, user.get()); 
                    datasetRepository.save(dataset);

                    // Create a unique ID for this Dataset object
                    String dataObjectId = UUID.randomUUID().toString();
                    request.getSession().setAttribute(dataObjectId, dataset);

                    // Go to the page for ChartServlet
                    return "redirect:/data/graph?userId="+userId+"&datasetId="+dataset.getId()+"&dataObjectId="+dataObjectId;
                }   
            }
        }
        return "data/upload";
    }

    @GetMapping("graph")
    public String showGraph(@RequestParam String dataObjectId, Integer userId, Integer datasetId, @RequestParam(required=false) boolean alreadySaved, Model model){
        model.addAttribute("dataObjectId", dataObjectId);
        model.addAttribute("datasetId", datasetId);
        if(userId != null){
            model.addAttribute("userId", userId);
        }
        if(alreadySaved == true){
            model.addAttribute("alreadySaved", true);
        }
        return "data/graph";
    }

    @PostMapping("delete")
    public String processDelete(@RequestParam(required=false) Integer userId, @RequestParam(required=false) String goTo, @RequestParam Integer datasetId, Model model){
        if(userId == null){
            datasetRepository.deleteById(datasetId);

            if(goTo != null && goTo.equals("signup")){
                return "redirect:/user/signup";
            }
            return "redirect:/data/upload";
        }

        Optional<Dataset> data = datasetRepository.findById(datasetId);
        if(data.isPresent()){
            if(data.get().getOwner().getId() == userId){
                datasetRepository.deleteById(datasetId);
                if(goTo != null && goTo.equals("account")){
                    return "redirect:/user/account?userId="+userId;
                }else if(goTo != null && goTo.equals("logout")){
                    return "redirect:/user/login";
                }
                return "redirect:/data/upload?userId="+userId;
            }else{
                model.addAttribute("deleteError", "Error deleting dataset");
                model.addAttribute("userId", userId);
                model.addAttribute("datasetId", datasetId);
                return "data/graph";
            }
        }
        return "redirect:/data/upload";
    }

    

}
