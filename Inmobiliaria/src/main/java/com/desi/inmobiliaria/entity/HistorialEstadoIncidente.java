package com.desi.inmobiliaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity // Indica que esta clase se guarda en la base de datos
public class HistorialEstadoIncidente {

   // Id único del registro
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   // Guarda el estado que tenía el incidente
   @Enumerated(EnumType.STRING)
   private EstadoIncidente estado;

   // Guarda la fecha y hora en que cambió el estado
   private LocalDateTime fechaHora;

   // Cada registro del historial pertenece a un incidente
   @ManyToOne(optional = false)
   private Incidente incidente;

   // Constructor vacío
   public HistorialEstadoIncidente() {
   }

   // Lo uso para crear un nuevo registro cada vez que cambia el estado
   public HistorialEstadoIncidente(Incidente incidente, EstadoIncidente estado) {

      this.incidente = incidente;
      this.estado = estado;

      // Guarda automáticamente la fecha y hora del cambio
      this.fechaHora = LocalDateTime.now();
   }

   // Devuelve el id
   public Long getId() {
      return this.id;
   }

   // Devuelve el estado
   public EstadoIncidente getEstado() {
      return this.estado;
   }

   // Cambia el estado
   public void setEstado(EstadoIncidente estado) {
      this.estado = estado;
   }

   // Devuelve la fecha y hora
   public LocalDateTime getFechaHora() {
      return this.fechaHora;
   }

   // Cambia la fecha y hora
   public void setFechaHora(LocalDateTime fechaHora) {
      this.fechaHora = fechaHora;
   }

   // Devuelve el incidente
   public Incidente getIncidente() {
      return this.incidente;
   }

   // Cambia el incidente
   public void setIncidente(Incidente incidente) {
      this.incidente = incidente;
   }
}
