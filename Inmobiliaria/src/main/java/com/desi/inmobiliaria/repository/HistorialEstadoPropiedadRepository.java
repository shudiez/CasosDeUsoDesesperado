package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.inmobiliaria.entity.HistorialEstadoPropiedad;

//Repositorio para manejar los registros del historial de estados
public interface HistorialEstadoPropiedadRepository extends JpaRepository<HistorialEstadoPropiedad, Long> {

}
