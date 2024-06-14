package com.example.demo.controller;

import com.example.demo.model.Persona;
import com.example.demo.repo.IPersonaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonaController {

    @Autowired
    private IPersonaRepo repo;

    // Endpoint para verificar que la API está activa
    @GetMapping("/")
    public String isLive() {
        return "API is live!";
    }

    // Obtener todas las personas
    @GetMapping("/personas")
    public List<Persona> getPersonas() {
        return repo.findAll();
    }

    // Obtener una persona por ID
    @GetMapping("/persona/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable("id") int id) {
        Optional<Persona> personaOptional = repo.findById(id);
        return personaOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva persona
    @PostMapping("/persona")
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        try {
            Persona nuevaPersona = repo.save(persona);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPersona);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar una persona existente
    @PutMapping("/persona/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable("id") int id, @RequestBody Persona persona) {
        Optional<Persona> personaOptional = repo.findById(id);
        if (personaOptional.isPresent()) {
            Persona existingPersona = personaOptional.get();
            existingPersona.setEmail(persona.getEmail());
            existingPersona.setPassword(persona.getPassword());
            existingPersona.setName(persona.getName());
            existingPersona.setLastname(persona.getLastname());

            Persona updatedPersona = repo.save(existingPersona);
            return ResponseEntity.ok(updatedPersona);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una persona
    @DeleteMapping("/persona/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable("id") int id) {
        try {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
