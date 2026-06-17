package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.desi.inmobiliaria.entity.HistorialEstadoFactura;

@Repository
public interface HistorialEstadoFacturaRepository extends JpaRepository<HistorialEstadoFactura, Long> {

}
