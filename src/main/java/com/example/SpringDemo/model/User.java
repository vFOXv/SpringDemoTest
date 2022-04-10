package com.example.SpringDemo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue //(strategy = GenerationType.IDENTITY) = @Transactional над методом loadUserByUsername в UserService
    private Long id;

    private String username;

    private String password;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Collection<Role> roles;
}
