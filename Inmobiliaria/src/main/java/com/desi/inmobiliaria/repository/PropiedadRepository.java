package com.desi.inmobiliaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;

@Repository
// Repository que se encarga de acceder a las propiedades
public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {

	// Verifica si ya existe una propiedad con la misma dirección en la misma ciudad
	boolean existsByDireccionAndCiudad(String direccion, Ciudad ciudad);

	// Devuelve solo las propiedades que no fueron eliminadas
	List<Propiedad> findByEliminadaFalse();

	// Busca una propiedad por su dirección y ciudad
	Propiedad findByDireccionAndCiudad(String direccion, Ciudad ciudad);

	// Busca propiedades cuya dirección contenga el texto ingresado
	// sin importar mayúsculas o minúsculas
	List<Propiedad> findByDireccionContainingIgnoreCaseAndEliminadaFalse(String direccion);

	// Busca las propiedades de una ciudad
	List<Propiedad> findByCiudadIdAndEliminadaFalse(Long ciudadId);

	// Busca propiedades según su tipo
	List<Propiedad> findByTipoAndEliminadaFalse(TipoPropiedad tipo);

	// Busca propiedades según su estado de disponibilidad
	List<Propiedad> findByEstadoDisponibilidadAndEliminadaFalse(EstadoDisponibilidad estado);

	// Verifica si un propietario tiene propiedades asociadas
	boolean existsByPropietarioId(Long propietarioId);

	// Busca propiedades combinando varios filtros
	@Query("SELECT p FROM Propiedad p WHERE p.eliminada = FALSE "
			+ "AND (:direccion IS NULL OR p.direccion LIKE %:direccion%) "
			+ "AND (:ciudadId IS NULL OR p.ciudad.id = :ciudadId) "
			+ "AND (:tipo IS NULL OR p.tipo = :tipo) "
			+ "AND (:estado IS NULL OR p.estadoDisponibilidad = :estado)")
	List<Propiedad> buscarConFiltros(
			@Param("direccion") String direccion,
			@Param("ciudadId") Long ciudadId,
			@Param("tipo") TipoPropiedad tipo,
			@Param("estado") EstadoDisponibilidad estado);

	// Verifica si una ciudad tiene propiedades asociadas
	boolean existsByCiudadId(Long ciudadId);

	// Devuelve solamente las propiedades disponibles
	@Query("SELECT p FROM Propiedad p WHERE p.eliminada = FALSE "
			+ "AND p.estadoDisponibilidad = 'DISPONIBLE'")
	List<Propiedad> buscarDisponibles();
}
