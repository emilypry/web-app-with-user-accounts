package com.wordpress.boxofcubes.webappwithuseraccounts.controllers;

import com.wordpress.boxofcubes.webappwithuseraccounts.data.UserRepository;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController{
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("login")
    public String showLogin(){
        return "user/login";
    }
    

    @GetMapping("signup")
    public String showSignup(){
        return "user/signup";
    }
    @PostMapping("signup")
    @ResponseBody
    public String processSignup(@ModelAttribute User user){
        userRepository.save(user);
        return "ok, added "+user.getUsername();
    }

}