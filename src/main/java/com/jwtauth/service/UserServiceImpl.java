package com.jwtauth.service;

import com.jwtauth.dtos.UserDTO;
import com.jwtauth.entities.Role;
import com.jwtauth.entities.User;
import com.jwtauth.repository.RoleRepository;
import com.jwtauth.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = this.mapper.map(userDTO, User.class);
        user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        Set<Role> set = new HashSet<>();
        for (Role role : userDTO.getRoles()) {
            Optional<Role> byId = this.roleRepository.findById(role.getRoleid());
            if (byId.isPresent()) {
                set.add(byId.get());
            }
        }
        user.setRoles(set);
        user = this.userRepository.save(user);
        return this.mapper.map(user, UserDTO.class);
    }
}