package com.desi.inmobiliaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.inmobiliaria.entity.EstadoIncidente;
import com.desi.inmobiliaria.entity.Incidente;
import com.desi.inmobiliaria.entity.Propiedad;

public interface IncidenteRepository extends JpaRepository<Incidente, Long> {

	// Devuelve solamente los incidentes no eliminados
	List<Incidente> findByEliminadoFalse();

	// Busca los incidentes de una propiedad
	List<Incidente> findByPropiedadAndEliminadoFalse(Propiedad propiedad);

	// Busca incidentes por estado
	List<Incidente> findByEstadoAndEliminadoFalse(EstadoIncidente estado);

}
