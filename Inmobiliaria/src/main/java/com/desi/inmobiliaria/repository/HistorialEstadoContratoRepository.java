package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.HistorialEstadoContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialEstadoContratoRepository extends JpaRepository<HistorialEstadoContrato, Long> {
   
}