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
public class HistorialEstadoIncidente {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   @Enumerated(EnumType.STRING)
   private EstadoIncidente estado;
   private LocalDateTime fechaHora;
   @ManyToOne(
      optional = false
   )
   private Incidente incidente;

   public HistorialEstadoIncidente() {
   }

   public HistorialEstadoIncidente(Incidente incidente, EstadoIncidente estado) {
      this.incidente = incidente;
      this.estado = estado;
      this.fechaHora = LocalDateTime.now();
   }

   public Long getId() {
      return this.id;
   }

   public EstadoIncidente getEstado() {
      return this.estado;
   }

   public void setEstado(EstadoIncidente estado) {
      this.estado = estado;
   }

   public LocalDateTime getFechaHora() {
      return this.fechaHora;
   }

   public void setFechaHora(LocalDateTime fechaHora) {
      this.fechaHora = fechaHora;
   }

   public Incidente getIncidente() {
      return this.incidente;
   }

   public void setIncidente(Incidente incidente) {
      this.incidente = incidente;
   }
}
