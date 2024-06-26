package com.example.demo.repo;

import com.example.demo.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPersonaRepo extends JpaRepository<Persona, Integer> {
    Optional<Persona> findByEmail(String email);


}

