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
public class HistorialEstadoPropiedad {

   // Id único del registro
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   // Guarda el estado que tenía la propiedad
   @Enumerated(EnumType.STRING)
   private EstadoDisponibilidad estado;

   // Guarda la fecha y hora del cambio de estado
   private LocalDateTime fechaHora;

   // Cada registro del historial pertenece a una propiedad
   @ManyToOne(optional = false)
   private Propiedad propiedad;

   // Constructor vacío
   public HistorialEstadoPropiedad() {
   }

   // Devuelve el id
   public Long getId() {
      return this.id;
   }

   // Cambia el id
   public void setId(Long id) {
      this.id = id;
   }

   // Devuelve el estado
   public EstadoDisponibilidad getEstado() {
      return this.estado;
   }

   // Cambia el estado
   public void setEstado(EstadoDisponibilidad estado) {
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

   // Devuelve la propiedad
   public Propiedad getPropiedad() {
      return this.propiedad;
   }

   // Cambia la propiedad
   public void setPropiedad(Propiedad propiedad) {
      this.propiedad = propiedad;
   }
}
