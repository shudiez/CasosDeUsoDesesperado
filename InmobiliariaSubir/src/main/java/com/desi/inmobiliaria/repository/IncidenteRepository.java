package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.EstadoIncidente;
import com.desi.inmobiliaria.entity.Incidente;
import com.desi.inmobiliaria.entity.Propiedad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenteRepository extends JpaRepository<Incidente, Long> {
   List<Incidente> findByEliminadoFalse();

   List<Incidente> findByPropiedadAndEliminadoFalse(Propiedad propiedad);

   List<Incidente> findByEstadoAndEliminadoFalse(EstadoIncidente estado);
}
