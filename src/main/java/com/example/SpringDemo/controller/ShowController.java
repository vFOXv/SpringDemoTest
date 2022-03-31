package com.example.SpringDemo.controller;

import com.example.SpringDemo.model.Animal;
import com.example.SpringDemo.service.AnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/show")
public class ShowController {

    private final AnimalService animalService;

    public ShowController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/all")
    public String showAllAnimals(Model model){
        List<Animal> animals = animalService.getAllAnimals();
        model.addAttribute("AllAnimals", animals);
        model.addAttribute("DeleteThisAnimal", "Delete");
        return "Show/show_all_animals";
    }
}
