package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.HistorialEstadoIncidente;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository que se encarga de acceder a la tabla del historial de estados de los incidentes
public interface HistorialEstadoIncidenteRepository extends JpaRepository<HistorialEstadoIncidente, Long> {

   // No hace falta crear métodos porque JpaRepository ya trae
   // guardar, buscar, listar y eliminar.

}
