package com.jwtauth.controller;

import com.jwtauth.dtos.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api")
public class HomeController {


    @GetMapping("/home/user")
    public String userHome(){
        return "User Home";
    }


    @GetMapping("/home/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHome(){
        return "Admin Home";
    }



}
