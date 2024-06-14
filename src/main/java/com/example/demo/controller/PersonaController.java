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
    public ResponseEntity<?> createPersona(@RequestBody Persona persona) {
        try {
            // Verificar si ya existe una persona con el mismo email
            Optional<Persona> existingPersona = repo.findByEmail(persona.getEmail());
            if (existingPersona.isPresent()) {
                // Retornar un mensaje de error indicando que ya existe una persona con ese email
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El email proporcionado ya está registrado");
            }

            // Si no hay persona con el mismo email, guardar la nueva persona
            Persona nuevaPersona = repo.save(persona);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPersona);
        } catch (Exception e) {
            // Capturar cualquier excepción y retornar un error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/persona/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable("id") int id, @RequestBody Persona persona) {
        // Buscar la persona existente por su ID
        Optional<Persona> personaOptional = repo.findById(id);

        // Verificar si la persona existe en la base de datos
        if (personaOptional.isPresent()) {
            // Si existe, obtener la instancia de Persona
            Persona existingPersona = personaOptional.get();

            // Actualizar los campos de la persona con los nuevos valores proporcionados en el cuerpo de la solicitud
            existingPersona.setEmail(persona.getEmail());
            existingPersona.setPassword(persona.getPassword());
            existingPersona.setName(persona.getName());
            existingPersona.setLastname(persona.getLastname());

            // Guardar la persona actualizada en la base de datos
            Persona updatedPersona = repo.save(existingPersona);

            // Retornar una respuesta con el código 200 OK y la persona actualizada en el cuerpo de la respuesta
            return ResponseEntity.ok(updatedPersona);
        } else {
            // Si no se encuentra la persona por el ID, retornar una respuesta con el código 404 Not Found
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
