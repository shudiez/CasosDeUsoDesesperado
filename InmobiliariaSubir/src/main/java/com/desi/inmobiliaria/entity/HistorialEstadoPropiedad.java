package com.desi.inmobiliaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class HistorialEstadoPropiedad {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   @Enumerated(EnumType.STRING)
   private EstadoDisponibilidad estado;
   private LocalDateTime fechaHora;
   @ManyToOne(
      optional = false
   )
   private Propiedad propiedad;

   public HistorialEstadoPropiedad() {
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public EstadoDisponibilidad getEstado() {
      return this.estado;
   }

   public void setEstado(EstadoDisponibilidad estado) {
      this.estado = estado;
   }

   public LocalDateTime getFechaHora() {
      return this.fechaHora;
   }

   public void setFechaHora(LocalDateTime fechaHora) {
      this.fechaHora = fechaHora;
   }

   public Propiedad getPropiedad() {
      return this.propiedad;
   }

   public void setPropiedad(Propiedad propiedad) {
      this.propiedad = propiedad;
   }
}
