package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.Ciudad;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interfaz se usa para acceder a la base de datos
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

   // Busca una ciudad por su nombre
   Optional<Ciudad> findByNombre(String nombre);

   // Verifica si ya existe otra ciudad con el mismo nombre
   // (excepto la que se está editando)
   boolean existsByNombreAndIdNot(String nombre, Long id);

   // Verifica si una provincia tiene ciudades asociadas
   // Lo uso para no eliminar una provincia que todavía está en uso
   boolean existsByProvinciaId(Long provinciaId);
}
