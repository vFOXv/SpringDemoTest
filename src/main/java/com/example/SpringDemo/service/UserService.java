package com.example.SpringDemo.service;

import com.example.SpringDemo.model.Role;
import com.example.SpringDemo.model.User;
import com.example.SpringDemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //обертка над методом findByUsername(username) из Repository
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    //UserDetails содержит мин необходимых данных для аунтификации пользователя
    //коллекцию прав доступа, password and username
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //получаем из DB user по имени
        User user = findByUsername(username);
        //если в DB такого user нет кидаеться Exception
        if(user==null){
            throw new UsernameNotFoundException(String.format("User '%s' not found!", username));
        }
        //возвращаем UserDetails как SpringSecurityUser(НАШ user.getUsername(),
        // user.getPassword(), коллекция прав доступа)
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    //метод преобразующий коллекцию ролей(в нашем user)
    // в коллекцию прав доступа(Security user) для UserDetails
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    //получение списка всех записей из BD
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //запись в DB нового пользователя(user)
    public Boolean saveUser(User newUser){
        User userFromDB = userRepository.findByUsername(newUser.getUsername());
        if(userFromDB!=null) {//если уже существует
            return false;
        }
        userRepository.save(newUser);
        return true;
    }
}
