package com.example.apirest.prueba.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.apirest.prueba.entity.Persona;
import com.example.apirest.prueba.repository.PersonaRepository;


@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    

    @Transactional(readOnly = true)
    @Override
    public Optional<Persona> findById(Integer id) {
        return personaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Persona> findAll() {
        return personaRepository.findAll();
    }

    @Transactional()
    @Override
    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }

    @Transactional()
    @Override
    public void deleteById(Integer id) {
        personaRepository.deleteById(id);

    }


    
}
