package com.jwtauth.service;

import com.jwtauth.dtos.RoleDTO;
import com.jwtauth.dtos.UserDTO;

import java.util.List;

public interface RoleService {

    RoleDTO createRole(RoleDTO roleDTO);

    List<RoleDTO> getAllRoles();

    RoleDTO getRoleById(Integer roleid);

}
