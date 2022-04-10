package com.example.SpringDemo.controller;

import com.example.SpringDemo.model.Role;
import com.example.SpringDemo.model.User;
import com.example.SpringDemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


@Controller
@RequestMapping("/security")
public class SecurityController {

    private final RoleService roleService;

    @Autowired
    public SecurityController(RoleService roleService) {
        this.roleService = roleService;
    }

    //создает модель и предает ее на страницу HTML для создания новой записи в DB о новом User
    @GetMapping("/registration")
    public String newUser(Model model){
        User newUser = new User();
        //в newUser roles = null
        Collection<Role> roles = newUser.getRoles();
        //id user = 1    добовляем в список ролей User(все кто регестрируются будут иметь роль User)
        roles.add(roleService.findById(1L));
        newUser.setRoles(roles);
        model.addAttribute("newUser", newUser);
        return "Security/registration";
    }

}
