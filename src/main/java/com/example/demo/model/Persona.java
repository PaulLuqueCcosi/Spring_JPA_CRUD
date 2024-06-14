package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPersona;

    @Column(name = "email", length = 100, nullable = false, unique = true) // email es obligatorio y único
    private String email;

    @Column(name = "password", length = 100, nullable = false) // password es obligatorio
    private String password;

    @Column(name = "nombre", length = 50)
    private String name;

    @Column(name = "apellido", length = 50)
    private String lastname;

    public Persona() {
        // Constructor vacío requerido por JPA
    }

    public Persona(String email, String password, String name, String lastname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
