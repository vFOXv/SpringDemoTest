package com.example.SpringDemo.controller;

import com.example.SpringDemo.model.Role;
import com.example.SpringDemo.model.User;
import com.example.SpringDemo.service.RoleService;
import com.example.SpringDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.HashSet;


@Controller
@RequestMapping("/security")
public class SecurityController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public SecurityController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    //создает модель и предает ее на страницу HTML для создания новой записи в DB о новом User
    @GetMapping("/registration")
    public String newUser(Model model){
        User newUser = new User();
//        //в newUser roles = null
//        Collection<Role> roles = new HashSet<Role>();
//        //id user = 1    добовляем в список ролей User(все кто регестрируются будут иметь роль User)
//        roles.add(roleService.findById(1L));
//        newUser.setRoles(roles);
        model.addAttribute("newUser", newUser);
        System.out.println(newUser);
        return "Security/registration";
    }

    //получает данные с HTML страницы и сохраняет в DB нового пользователя
    @PostMapping("/registration")
    public String saveNewUserInDB(@ModelAttribute User newUser){
        System.out.println("--------------------------------->");
        System.out.println(newUser);

        //в newUser roles = null
        Collection<Role> roles = new HashSet<Role>();
        //id user = 1    добовляем в список ролей User(все кто регестрируются будут иметь роль User)
        roles.add(roleService.findById(1L));
        newUser.setRoles(roles);

        //шифрование пароля
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
        newUser.setPassword(encoder.encode(newUser.getPassword()));

        Boolean flag = userService.saveUser(newUser);
        if(!flag){
            System.err.println("This Name User already exists!!!");
        }
        return "redirect:/login";
    }
}
