package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.EstadoIncidente;
import com.desi.inmobiliaria.entity.Incidente;
import com.desi.inmobiliaria.entity.Propiedad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository que se encarga de acceder a los incidentes
public interface IncidenteRepository extends JpaRepository<Incidente, Long> {

   // Trae solo los incidentes que no fueron eliminados
   List<Incidente> findByEliminadoFalse();

   // Busca los incidentes de una propiedad
   // y que no estén eliminados
   List<Incidente> findByPropiedadAndEliminadoFalse(Propiedad propiedad);

   // Busca los incidentes según su estado
   // y que no estén eliminados
   List<Incidente> findByEstadoAndEliminadoFalse(EstadoIncidente estado);
}
