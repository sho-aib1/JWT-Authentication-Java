package com.jwtauth.service;

import com.jwtauth.Exception.ResouceNotFoundException;
import com.jwtauth.dtos.RoleDTO;
import com.jwtauth.entities.Role;
import com.jwtauth.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {

        if(roleDTO.getRolename().toLowerCase().contains("admin")){
            roleDTO.setRolename("ROLE_ADMIN");
        }
        else {
            roleDTO.setRolename("ROLE_USER");
        }
        Role role = this.modelMapper.map(roleDTO, Role.class);

        Role newRole = this.roleRepository.save(role);
        return this.modelMapper.map(newRole, RoleDTO.class);
    }

    @Override
    public List<RoleDTO> getAllRoles() {

        List<Role> roles = this.roleRepository.findAll();

        List<RoleDTO> collect = roles.stream().map((role -> this.modelMapper.map(role, RoleDTO.class))).collect(Collectors.toList());

        return collect;
    }

    @Override
    public RoleDTO getRoleById(Integer roleid) {

        Role role = this.roleRepository.findById(roleid).orElseThrow(() -> new ResouceNotFoundException("Role", roleid + ""));

        System.out.println(role.getRolename() +" "+role.getRoleid());

        return this.modelMapper.map(role, RoleDTO.class);
    }
}
