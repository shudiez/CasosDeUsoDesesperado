package com.desi.inmobiliaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.entity.Propiedad;

//Repositorio encargado de manejar las propiedades en la base de datos
public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {

	// Verifica si ya existe una propiedad con la misma dirección en la misma ciudad
	boolean existsByDireccionAndCiudad(String direccion, Ciudad ciudad);

	// Devuelve solo las propiedades que no fueron eliminadas
	List<Propiedad> findByEliminadaFalse();

	// Busca una propiedad según su dirección y ciudad
	Propiedad findByDireccionAndCiudad(String direccion, Ciudad ciudad);

}