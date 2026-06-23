package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.EstadoPublicacion;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.Publicacion;

import java.util.List;

@Repository 
public interface PublicacionesRepository extends JpaRepository<Publicacion, Long> {
    
    
    boolean existsByPropiedadIdAndEstado(Long propiedadId, EstadoPublicacion estado);

    
   
    boolean existsByPropiedadIdAndEstadoAndIdNot(Long propiedadId, EstadoPublicacion estado, Long id);

   
    @Query("SELECT p FROM Propiedad p WHERE p.eliminada = false AND p.estadoDisponibilidad = :estado")
    List<Propiedad> buscarPropiedadesDisponibles(@Param("estado") EstadoDisponibilidad estado);

    
    @Query("SELECT p FROM Propiedad p WHERE p.id = :id")
    Propiedad buscarPropiedadPorId(@Param("id") Long id);

    List<Publicacion> findByEliminadaFalse();
}