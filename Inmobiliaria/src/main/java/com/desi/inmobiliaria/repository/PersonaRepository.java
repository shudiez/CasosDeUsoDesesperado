package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desi.inmobiliaria.entity.Persona;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

	Optional<Persona> findByDniCuit(String dniCuit);

	boolean existsByDniCuitAndIdNot(String dniCuit, Long id);
}