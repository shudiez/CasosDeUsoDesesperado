package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.desi.inmobiliaria.entity.Persona;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

	Optional<Persona> findByDniCuit(String dniCuit);

	boolean existsByDniCuitAndIdNot(String dniCuit, Long id);
}