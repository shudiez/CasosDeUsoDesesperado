package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.Ciudad;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
   Optional<Ciudad> findByNombre(String nombre);

   boolean existsByNombreAndIdNot(String nombre, Long id);

   boolean existsByProvinciaId(Long provinciaId);
}
