package com.example.apirest.prueba.service;

import java.util.Optional;

import com.example.apirest.prueba.entity.Persona;

public interface PersonaService {
    //Read
    public Optional<Persona> findById(Integer id);

    public Iterable<Persona> findAll();

    //Create-Update
    public Persona save(Persona persona);

    
    //Delete
    public void deleteById(Integer id);
}
