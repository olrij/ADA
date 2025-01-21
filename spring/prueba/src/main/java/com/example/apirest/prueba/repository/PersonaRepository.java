package com.example.apirest.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apirest.prueba.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona,Integer> {
    
}
