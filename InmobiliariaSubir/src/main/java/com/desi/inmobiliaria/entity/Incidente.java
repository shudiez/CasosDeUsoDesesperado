package com.desi.inmobiliaria.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Incidente {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   @Column(
      length = 200
   )
   private String titulo;
   @Column(
      length = 2000
   )
   private String descripcion;
   @Enumerated(EnumType.STRING)
   private CategoriaIncidente categoria;
   private LocalDateTime fechaAlta;
   @Enumerated(EnumType.STRING)
   private PrioridadIncidente prioridad;
   @Enumerated(EnumType.STRING)
   private EstadoIncidente estado;
   private boolean eliminado = false;
   private LocalDateTime fechaResolucion;
   @Column(
      length = 2000
   )
   private String observacionesResolucion;
   private BigDecimal costoResolucion;
   private String responsableTecnico;
   @ManyToOne(
      optional = false
   )
   private Propiedad propiedad;
   @OneToMany(
      mappedBy = "incidente",
      cascade = {CascadeType.ALL},
      orphanRemoval = true
   )
   private List<HistorialEstadoIncidente> historialEstados = new ArrayList();

   public Incidente() {
   }

   public void agregarCambioEstado(EstadoIncidente nuevoEstado) {
      HistorialEstadoIncidente historial = new HistorialEstadoIncidente(this, nuevoEstado);
      this.historialEstados.add(historial);
      this.estado = nuevoEstado;
   }

   public Long getId() {
      return this.id;
   }

   public String getTitulo() {
      return this.titulo;
   }

   public void setTitulo(String titulo) {
      this.titulo = titulo;
   }

   public String getDescripcion() {
      return this.descripcion;
   }

   public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
   }

   public CategoriaIncidente getCategoria() {
      return this.categoria;
   }

   public void setCategoria(CategoriaIncidente categoria) {
      this.categoria = categoria;
   }

   public LocalDateTime getFechaAlta() {
      return this.fechaAlta;
   }

   public void setFechaAlta(LocalDateTime fechaAlta) {
      this.fechaAlta = fechaAlta;
   }

   public PrioridadIncidente getPrioridad() {
      return this.prioridad;
   }

   public void setPrioridad(PrioridadIncidente prioridad) {
      this.prioridad = prioridad;
   }

   public EstadoIncidente getEstado() {
      return this.estado;
   }

   public void setEstado(EstadoIncidente estado) {
      this.estado = estado;
   }

   public boolean isEliminado() {
      return this.eliminado;
   }

   public void setEliminado(boolean eliminado) {
      this.eliminado = eliminado;
   }

   public LocalDateTime getFechaResolucion() {
      return this.fechaResolucion;
   }

   public void setFechaResolucion(LocalDateTime fechaResolucion) {
      this.fechaResolucion = fechaResolucion;
   }

   public String getObservacionesResolucion() {
      return this.observacionesResolucion;
   }

   public void setObservacionesResolucion(String observacionesResolucion) {
      this.observacionesResolucion = observacionesResolucion;
   }

   public BigDecimal getCostoResolucion() {
      return this.costoResolucion;
   }

   public void setCostoResolucion(BigDecimal costoResolucion) {
      this.costoResolucion = costoResolucion;
   }

   public String getResponsableTecnico() {
      return this.responsableTecnico;
   }

   public void setResponsableTecnico(String responsableTecnico) {
      this.responsableTecnico = responsableTecnico;
   }

   public Propiedad getPropiedad() {
      return this.propiedad;
   }

   public void setPropiedad(Propiedad propiedad) {
      this.propiedad = propiedad;
   }

   public List<HistorialEstadoIncidente> getHistorialEstados() {
      return this.historialEstados;
   }
}
