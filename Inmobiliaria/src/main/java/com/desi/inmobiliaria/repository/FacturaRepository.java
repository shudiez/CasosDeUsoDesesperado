package com.desi.inmobiliaria.repository;

import com.desi.inmobiliaria.entity.EstadoFactura;
import com.desi.inmobiliaria.entity.Factura;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
   @Query("SELECT f FROM Factura f WHERE f.eliminado = FALSE AND (:contratoId IS NULL OR f.contrato.id = :contratoId) AND (:inquilinoId IS NULL OR f.contrato.inquilino.id = :inquilinoId) AND (:propiedadId IS NULL OR f.contrato.propiedad.id = :propiedadId) AND (:estado IS NULL OR f.estado = :estado) AND (:desde IS NULL OR f.fechaVencimiento >= :desde) AND (:hasta IS NULL OR f.fechaVencimiento <= :hasta)")
   List<Factura> buscarConFiltros(@Param("contratoId") Long contratoId, @Param("inquilinoId") Long inquilinoId, @Param("propiedadId") Long propiedadId, @Param("estado") EstadoFactura estado, @Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);
}
