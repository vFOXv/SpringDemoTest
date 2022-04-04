package com.example.SpringDemo.service;

import com.example.SpringDemo.model.Animal;
import com.example.SpringDemo.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    //получение списка всех записей из BD
    public List<Animal> getAllAnimals(){
        return animalRepository.findAll();
    }

    //поиск записи в DB по id
    public Animal searchById(Long id){
        return animalRepository.getById(id);
    }

    //сохранение нового животного в DB
    public void saveByDb(Animal animal){
        animalRepository.save(animal);
    }

    //удаление конкретного животного из DB
    public void deleteByDb(Long id){
        Animal animal = searchById(id);
        animalRepository.delete(animal);
    }

}
