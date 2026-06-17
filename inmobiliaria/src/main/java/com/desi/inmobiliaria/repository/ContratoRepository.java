package com.desi.inmobiliaria.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.desi.inmobiliaria.entity.*;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
	boolean existsByPropiedadAndEstadoAndEliminadoFalse(Propiedad propiedad, EstadoContrato estado);

	Contrato findByPropiedadAndEstadoAndEliminadoFalse(Propiedad propiedad, EstadoContrato estado);
	// Cambiás findBy... por findFirstBy...
	// Contrato findFirstByPropiedadAndEstadoAndEliminadoFalse(Propiedad propiedad,
	// EstadoContrato estado);

	@Query("SELECT c FROM Contrato c WHERE " + "(:direccion IS NULL OR c.propiedad.direccion LIKE %:direccion%) AND "
			+ "(:inquilino IS NULL OR c.inquilino.apellido LIKE %:inquilino% OR c.inquilino.nombre LIKE %:inquilino%) AND "
			+ "(:estado IS NULL OR c.estado = :estado) AND " + "(:fechaInicio IS NULL OR c.fechaInicio = :fechaInicio)")
	List<Contrato> filtrarContratos(@Param("direccion") String direccion, @Param("inquilino") String inquilino,
			@Param("estado") EstadoContrato estado, @Param("fechaInicio") LocalDate fechaInicio);

	List<Contrato> findByPropiedadIdAndEstado(Long propiedadId, EstadoContrato estado);
}