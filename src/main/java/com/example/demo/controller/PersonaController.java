package com.example.demo.controller;

import com.example.demo.model.Persona;
import com.example.demo.repo.IPersonaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PersonaController {
    @Autowired
    private IPersonaRepo repo;

    @GetMapping("/")
    public String isLive(){
        return "HOLAAAA";
    }

    @GetMapping("/persona")
    public List<Persona> getPersona() {
        List<Persona> personas = repo.findAll();

        return personas;
    }

    @PostMapping("/persona")
    public Persona createPersona(@RequestBody Persona person) {
        repo.save(person);
        return person;
    }


}
