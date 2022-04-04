package com.example.SpringDemo.controller;

import com.example.SpringDemo.model.Animal;
import com.example.SpringDemo.service.AnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/action")
public class ActionController {
    private final AnimalService animalService;

    public ActionController(AnimalService animalService) {
        this.animalService = animalService;
    }

    //создает модель и предает ее на страницу HTML для создания новой записи в DB
    @GetMapping("/recordNewAnimalInDb")
    public String openPageRecordNewAnimalInDb(Model model){
        Animal animal = new Animal();
        model.addAttribute("newAnimal", animal);
        return "Action/record_in_db";
    }

    //получение данных о новой записи и запись ее в DB
    @PostMapping("/recordNewAnimalInDb")
    public String recordInDbNewAnimal(@ModelAttribute("newAnimal") Animal animal){
        animalService.saveByDb(animal);
        return "redirect:/show/all";
    }

    //удаление конкретного животного из DB
    @GetMapping("/deleteThisAnimal/{id}")
    public String deleteThisAnimalByDb(@PathVariable("id") Long id){
        animalService.deleteByDb(id);
        return "redirect:/show/all";
    }

    //удаление конкретного животного из DB
    @DeleteMapping("/ThisAnimalMethodDelete/{id}")
    public String methodDeleteThisAnimalByDb(@PathVariable("id") Long id){
        animalService.deleteByDb(id);
        return "redirect:/show/all";
    }
}
