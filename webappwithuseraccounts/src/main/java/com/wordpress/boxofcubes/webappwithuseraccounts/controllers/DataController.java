package com.wordpress.boxofcubes.webappwithuseraccounts.controllers;

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
    public String processUpload(@RequestParam("xFile") MultipartFile xFile){
        return xFile.getOriginalFilename();
    }
}
