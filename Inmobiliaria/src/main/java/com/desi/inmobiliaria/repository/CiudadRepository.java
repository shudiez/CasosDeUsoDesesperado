package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.desi.inmobiliaria.entity.Ciudad;
import java.util.List;
import java.util.Optional;

//Se usa para guardar, buscar y listar ciudades en la base de datos
@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

	Optional<Ciudad> findByNombre(String nombre);

	boolean existsByNombreAndIdNot(String nombre, Long id);

	boolean existsByProvinciaId(Long provinciaId);


}
