package com.jwtauth.controller;

import com.jwtauth.dtos.RoleDTO;
import com.jwtauth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("roles/create")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO role = this.roleService.createRole(roleDTO);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @GetMapping("roles/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") Integer id) {
        RoleDTO roleById = this.roleService.getRoleById(id);
        return new ResponseEntity<>(roleById, HttpStatus.FOUND);
    }

    @GetMapping("roles/")
    public ResponseEntity<List<RoleDTO>> getAllRole() {
        List<RoleDTO> allRoles = this.roleService.getAllRoles();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }

}
