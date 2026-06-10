package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.desi.entity.Persona;

public interface PersonaRepository extends  JpaRepository <Persona, Long>  {

}

