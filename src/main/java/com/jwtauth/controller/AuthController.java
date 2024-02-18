package com.jwtauth.controller;

import com.jwtauth.dtos.ApiResponse;
import com.jwtauth.dtos.JWTRequest;
import com.jwtauth.dtos.JWTResponse;
import com.jwtauth.dtos.UserDTO;
import com.jwtauth.entities.User;
import com.jwtauth.security.JwtTokenHelper;
import com.jwtauth.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api")
public class AuthController {


    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<JWTResponse> createToken(@RequestBody JWTRequest request) throws Exception {
        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        System.out.println("-------------------"+token);
        JWTResponse response = new JWTResponse();
        response.setToken(token);
        //response.setUser(this.mapper.map((User) userDetails, UserDTO.class));
        return new ResponseEntity<JWTResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

        try {

            this.authenticationManager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Detials !!");
            System.out.println("Invalid Password and username ");
        }

    }

    @PostMapping("auth/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

        UserDTO user = this.userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @GetMapping("/currentUser")
    public UserDTO getCurrentUser(Principal principal) {
        UserDetails userDetails =  this.userDetailsService.loadUserByUsername(principal.getName());
        return (UserDTO) userDetails;
    }

}
