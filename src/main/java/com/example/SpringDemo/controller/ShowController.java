package com.example.SpringDemo.controller;

import com.example.SpringDemo.model.Animal;
import com.example.SpringDemo.service.AnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/show")
public class ShowController {

    private final AnimalService animalService;

    public ShowController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/all")
    //Principal краткая информация о user
    public String showAllAnimals(Model model, Principal principal){
        List<Animal> animals = animalService.getAllAnimals();
        //Получение имени user
        model.addAttribute("NameUser", principal.getName());
        model.addAttribute("AllAnimals", animals);
        model.addAttribute("ThisAnimal", "This animal");
        return "Show/show_all_animals";
    }

    @GetMapping("/{id}")
    public String showThisAnimal(@PathVariable("id") Long id, Model model){
        Animal animal = new Animal();
        animal = animalService.searchById(id);
        model.addAttribute("thisAnimal", animal);
        model.addAttribute("Delete", "DELETE");
        return "Show/show_this_animal";
    }

    @GetMapping("/methodDelete/{id}")
    public String showThisAnimalMethodDelete(@PathVariable("id") Long id, Model model){
        Animal animal = new Animal();
        animal = animalService.searchById(id);
        model.addAttribute("thisAnimal", animal);
        model.addAttribute("Delete", "Method DELETE");
        return "Show/show_this_animal_delete";
    }
}
