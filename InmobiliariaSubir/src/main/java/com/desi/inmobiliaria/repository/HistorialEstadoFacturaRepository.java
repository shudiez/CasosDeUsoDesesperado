package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.HistorialEstadoFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialEstadoFacturaRepository extends JpaRepository<HistorialEstadoFactura, Long> {
}
