package com.jwtauth.dtos;

import com.jwtauth.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {

    private int userid;
    private String name;
    private String password;
    private String email;

    private Set<Role> roles = new HashSet<>();


}
