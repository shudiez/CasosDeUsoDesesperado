package com.desi.inmobiliaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;

//Repositorio encargado de manejar las propiedades en la base de datos
public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {

	// Verifica si ya existe una propiedad con la misma dirección en la misma ciudad
	boolean existsByDireccionAndCiudad(String direccion, Ciudad ciudad);

	// Devuelve solo las propiedades que no fueron eliminadas
	List<Propiedad> findByEliminadaFalse();

	// Busca una propiedad según su dirección y ciudad
	Propiedad findByDireccionAndCiudad(String direccion, Ciudad ciudad);

	List<Propiedad> findByDireccionContainingIgnoreCaseAndEliminadaFalse(String direccion);

	List<Propiedad> findByCiudadIdAndEliminadaFalse(Long ciudadId);

	List<Propiedad> findByTipoAndEliminadaFalse(TipoPropiedad tipo);

	List<Propiedad> findByEstadoDisponibilidadAndEliminadaFalse(EstadoDisponibilidad estado);

}