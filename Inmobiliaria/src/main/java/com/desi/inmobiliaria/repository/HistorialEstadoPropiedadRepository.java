package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.HistorialEstadoPropiedad;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository que se encarga de acceder al historial de estados de las propiedades
public interface HistorialEstadoPropiedadRepository extends JpaRepository<HistorialEstadoPropiedad, Long> {

   // No agregué métodos porque JpaRepository ya trae
   // los básicos para guardar, buscar, listar y eliminar.

}
