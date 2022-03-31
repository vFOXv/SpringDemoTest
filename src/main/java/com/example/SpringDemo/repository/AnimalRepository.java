package com.example.SpringDemo.repository;

import com.example.SpringDemo.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Animal findAnimalById(Long id);
}
