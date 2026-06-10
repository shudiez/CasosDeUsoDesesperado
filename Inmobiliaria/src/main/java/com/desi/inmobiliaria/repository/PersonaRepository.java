package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.inmobiliaria.entity.Persona;

//Repositorio encargado de manejar los datos de las personas
public interface PersonaRepository extends JpaRepository<Persona, Long> {

}