package com.irithir.controller;

import com.irithir.dto.RegistrationDto;
import com.irithir.model.UserEntity;
import com.irithir.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView loginPage(ModelAndView modelAndView){
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView){
        RegistrationDto user = new RegistrationDto();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register.html");
        return modelAndView;
    }

    @PostMapping("/register/save")
    public ModelAndView saveUser(@Valid @ModelAttribute("user") RegistrationDto user,
                                 BindingResult bindingResult,
                                 ModelAndView modelAndView){
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());

        System.out.println("here");

        if(existingUserEmail != null
                && existingUserEmail.getEmail() != null
                && !existingUserEmail.getEmail().isEmpty()){
            modelAndView.setViewName("redirect:/register?fail");
            return modelAndView;
        }

        UserEntity existingUserUsername = userService.findByUsername(user.getUsername());

        if(existingUserUsername != null
                && existingUserUsername.getUsername() != null
                && !existingUserUsername.getUsername().isEmpty()){
            modelAndView.setViewName("redirect:/register?fail");
            return modelAndView;
        }

        if(bindingResult.hasErrors()){
            modelAndView.addObject("user", user);
            modelAndView.setViewName("register.html");
            return modelAndView;
        }

        userService.saveUser(user);
        modelAndView.setViewName("redirect:/login");

        return modelAndView;
    }
}
