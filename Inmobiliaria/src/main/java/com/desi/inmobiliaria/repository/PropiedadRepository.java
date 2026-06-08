package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.entity.Propiedad;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {

	boolean existsByDireccionAndCiudad(String direccion, Ciudad ciudad);
}