package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.Provincia;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Repository que se encarga de acceder a las provincias
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

   // Busca una provincia por su nombre
   Optional<Provincia> findByNombre(String nombre);

   // Verifica si ya existe otra provincia con el mismo nombre
   // (excepto la que se está editando)
   boolean existsByNombreAndIdNot(String nombre, Long id);

}
