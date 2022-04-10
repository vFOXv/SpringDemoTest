package com.example.SpringDemo.service;

import com.example.SpringDemo.model.Role;
import com.example.SpringDemo.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    //поиск роли по id
    public Role findById(Long id){
        return roleRepository.findRoleById(id);
    }


}
