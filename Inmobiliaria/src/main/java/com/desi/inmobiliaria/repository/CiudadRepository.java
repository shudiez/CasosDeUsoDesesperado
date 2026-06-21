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
	/*
	 * // notar que aquí debí indicarle a JPA qué query espero que genere porque no
	 * le // estoy pasando como parametro la "Provincia" sino su Id // Traeme todas
	 * las ciudades cuyo nombre coincida O pertenezcan a esa provincia.
	 * 
	 * @Query("SELECT c FROM Ciudad c WHERE c.nombre like :nombre or c.provincia.id=:idProvinciaSeleccionada"
	 * ) List<Ciudad> findByNombreOrIdProvincia(String nombre, Long
	 * idProvinciaSeleccionada);
	 * 
	 * // Deben cumplirse las dos condiciones.
	 * 
	 * @Query("SELECT c FROM Ciudad c WHERE c.nombre like :nombre and c.provincia.id=:idProvinciaSeleccionada"
	 * ) List<Ciudad> findByNombreAndIdProvincia(String nombre, Long
	 * idProvinciaSeleccionada);
	 * 
	 * // busca las ciudades que coincidan con el nombre indicado y no coincidan con
	 * el // id indicado
	 * 
	 * @Query("SELECT c FROM Ciudad c WHERE c.nombre like :nombre and c.provincia.id=:idProvinciaSeleccionada and c.id<>:idDistintoDe"
	 * ) List<Ciudad> findOtraCiudadByNombreAndProvincia(String nombre, Long
	 * idProvinciaSeleccionada, Long idDistintoDe);
	 * 
	 */

}
