package com.wordpress.boxofcubes.webappwithuseraccounts.controllers;

import javax.validation.Valid;

import com.wordpress.boxofcubes.webappwithuseraccounts.data.UserRepository;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.User;

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


@Controller
@RequestMapping("user")
public class UserController{
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("login")
    public String showLogin(){
        return "user/login";
    }
    @PostMapping("login")
    @ResponseBody
    public String processLogin(@RequestParam String username, String password){
        User user = userRepository.findByUsername(username);

        if(user == null){
            return "no user with that name";
        }

        return String.valueOf(user.getId()+user.getUsername()+user.getPassword());
        //return "redirect:/data";
    }
    

    @GetMapping("signup")
    public String showSignup(Model model){
        model.addAttribute("user", new User());
        return "user/signup";
    }
    @PostMapping("signup")
    public String processSignup(@ModelAttribute @Valid User user, Errors errors, 
                                @RequestParam String retyped, Model model){

        boolean identical = user.getPassword().equals(retyped);

        if(identical==false || errors.hasErrors()){
            if(identical == false){
                model.addAttribute("passError", "Passwords must be identical");
            }
            return "user/signup";
        }

        // CHECK THAT NO USER WITH THAT USERNAME ALREADY EXISTS!!!!
        userRepository.save(user);
        return "redirect:/data";
    }
}