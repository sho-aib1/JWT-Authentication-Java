package com.jwtauth.service;

import com.jwtauth.dtos.UserDTO;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    
}
