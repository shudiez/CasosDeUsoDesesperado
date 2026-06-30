package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desi.inmobiliaria.entity.Provincia;
import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

	Optional<Provincia> findByNombre(String nombre);

	boolean existsByNombreAndIdNot(String nombre, Long id);
}