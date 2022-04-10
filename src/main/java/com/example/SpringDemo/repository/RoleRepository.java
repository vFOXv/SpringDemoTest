package com.example.SpringDemo.repository;

import com.example.SpringDemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findRoleById(Long id);
}
