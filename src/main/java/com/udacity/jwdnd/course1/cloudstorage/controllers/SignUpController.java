package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final UserService userService;

    public SignUpController(CredentialService credentialService, UserService userService) {
        this.userService = userService;
    }

    public final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @GetMapping()
    public String signupPage(){
        return "signup";
    }

    @PostMapping()
    public String signup(@ModelAttribute User user, Model model){
        if (user == null){
            model.addAttribute("signupError", "Invalid input");
            return "signup";
        }

        if(!userService.isUsernameExist(user.getUsername())){
            model.addAttribute("signupError", "Username is already exists");
            return "signup";
        }

        try {
            userService.createUser(user);
        } catch (Exception e){
            logger.error("sign up error ",e);
            model.addAttribute("signupError", "Sign up error");
            return "signup";
        }

        model.addAttribute("signupSuccess", true);
        return "signup";

    }
}
