package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.EstadoFactura;
import java.time.LocalDate;

public class FacturasBuscarForm {
   private Long contratoId;
   private Long inquilinoId;
   private Long propiedadId;
   private EstadoFactura estado;
   private LocalDate fechaDesde;
   private LocalDate fechaHasta;

   public FacturasBuscarForm() {
   }

   public Long getContratoId() {
      return this.contratoId;
   }

   public void setContratoId(Long contratoId) {
      this.contratoId = contratoId;
   }

   public Long getInquilinoId() {
      return this.inquilinoId;
   }

   public void setInquilinoId(Long inquilinoId) {
      this.inquilinoId = inquilinoId;
   }

   public Long getPropiedadId() {
      return this.propiedadId;
   }

   public void setPropiedadId(Long propiedadId) {
      this.propiedadId = propiedadId;
   }

   public EstadoFactura getEstado() {
      return this.estado;
   }

   public void setEstado(EstadoFactura estado) {
      this.estado = estado;
   }

   public LocalDate getFechaDesde() {
      return this.fechaDesde;
   }

   public void setFechaDesde(LocalDate fechaDesde) {
      this.fechaDesde = fechaDesde;
   }

   public LocalDate getFechaHasta() {
      return this.fechaHasta;
   }

   public void setFechaHasta(LocalDate fechaHasta) {
      this.fechaHasta = fechaHasta;
   }
}
