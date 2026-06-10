package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.entity.Propiedad;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

	boolean existsByPropiedadAndEstadoAndEliminadoFalse(Propiedad propiedad, EstadoContrato estado);

}