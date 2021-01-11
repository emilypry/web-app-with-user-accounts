package com.wordpress.boxofcubes.webappwithuseraccounts.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.wordpress.boxofcubes.webappwithuseraccounts.data.DatasetRepository;
import com.wordpress.boxofcubes.webappwithuseraccounts.data.UserRepository;
import com.wordpress.boxofcubes.webappwithuseraccounts.models.Dataset;
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

    @Autowired
    private DatasetRepository datasetRepository;
    
    @GetMapping("login")
    public String showLogin(){
        return "user/login";
    }
    @PostMapping("login")
    public String processLogin(@RequestParam String username, String password, Model model){
        User user = userRepository.findByUsername(username);

        if(user == null || user.getPassword().equals(password) == false){
            if(user == null){
                model.addAttribute("userError", "No account with that username");
            }else{
                model.addAttribute("passError", "Incorrect password");
            }
            return "user/login";
        }

        return "redirect:/data/upload?userId="+user.getId();
    }
    

    @GetMapping("signup")
    public String showSignup(Model model){
        model.addAttribute("user", new User());
        return "user/signup";
    }
    @PostMapping("signup")
    public String processSignup(@ModelAttribute @Valid User user, Errors errors, 
                                @RequestParam String retyped, Model model){

        User userExists = userRepository.findByUsername(user.getUsername());
        boolean identical = user.getPassword().equals(retyped);

        if(identical==false || userExists != null || errors.hasErrors()){
            if(identical == false){
                model.addAttribute("passError", "Passwords must be identical");
            }
            if(userExists != null){
                model.addAttribute("userExists", "That username is taken");
            }
            return "user/signup";
        }

        userRepository.save(user);
        return "redirect:/data/upload?userId="+user.getId();

    }

    @PostMapping("save")
    public String processSave(@RequestParam Integer userId, @RequestParam Integer datasetId, Integer dataObjectId, Model model){
        Optional<Dataset> data = datasetRepository.findById(datasetId);
        if(data.isPresent()){
            if(data.get().getOwner().getId() == userId){
                return "redirect:/user/account?userId="+userId;
            }else{
                model.addAttribute("saveError", "Error saving dataset");
                model.addAttribute("userId", userId);
                model.addAttribute("datasetId", datasetId);
                return "data/graph";
            }
        }
        return "redirect:/data/upload";
    }

    @GetMapping("account")
    public String showAccount(@RequestParam Integer userId, Model model){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            List<Dataset> datasets = datasetRepository.findByOwner(user.get());
            model.addAttribute("datasets", datasets);
            model.addAttribute("userId", userId);
            return "user/account";
        }
        return "redirect:/user/login";  
    }

    @PostMapping("account/view-dataset")
    public String processViewDataset(@RequestParam Integer userId, Integer datasetId, HttpServletRequest request, Model model){
        // Create a unique ID for this Dataset object
        String dataObjectId = UUID.randomUUID().toString();
        Optional<Dataset> data = datasetRepository.findById(datasetId);
        if(data.isPresent()){
            request.getSession().setAttribute(dataObjectId, data.get());
            return "redirect:/data/graph?userId="+userId+"&datasetId="+datasetId+"&dataObjectId="+dataObjectId;
        }
        return "redirect:/user/account?userId="+userId;
    }
}

