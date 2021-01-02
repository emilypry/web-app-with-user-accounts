package com.wordpress.boxofcubes.webappwithuseraccounts.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController{
    @GetMapping("")
    public String index(){
        return "index";
    }
}