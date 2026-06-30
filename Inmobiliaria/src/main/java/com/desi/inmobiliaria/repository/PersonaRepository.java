package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.Persona;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Repository que se encarga de acceder a los datos de Persona
public interface PersonaRepository extends JpaRepository<Persona, Long> {

   // Busca una persona por su DNI o CUIT
   Optional<Persona> findByDniCuit(String dniCuit);

   // Verifica si ya existe otra persona con el mismo DNI/CUIT
   // (excepto la que se está editando)
   boolean existsByDniCuitAndIdNot(String dniCuit, Long id);
}
