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

@Entity // Indica que esta clase se guarda en la base de datos
public class Incidente {

   // Id único del incidente
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   // Título del incidente
   @Column(length = 200)
   private String titulo;

   // Descripción del problema
   @Column(length = 2000)
   private String descripcion;

   // Categoría del incidente
   @Enumerated(EnumType.STRING)
   private CategoriaIncidente categoria;

   // Fecha en que se creó el incidente
   private LocalDateTime fechaAlta;

   // Prioridad del incidente
   @Enumerated(EnumType.STRING)
   private PrioridadIncidente prioridad;

   // Estado actual del incidente
   @Enumerated(EnumType.STRING)
   private EstadoIncidente estado;

   // Indica si fue eliminado de forma lógica
   private boolean eliminado = false;

   // Fecha en que se resolvió
   private LocalDateTime fechaResolucion;

   // Observaciones al resolver el incidente
   @Column(length = 2000)
   private String observacionesResolucion;

   // Costo de la reparación
   private BigDecimal costoResolucion;

   // Técnico responsable de resolverlo
   private String responsableTecnico;

   // Cada incidente pertenece a una propiedad
   @ManyToOne(optional = false)
   private Propiedad propiedad;

   // Guarda todos los cambios de estado del incidente
   @OneToMany(mappedBy = "incidente",
              cascade = CascadeType.ALL,
              orphanRemoval = true)
   private List<HistorialEstadoIncidente> historialEstados = new ArrayList<>();

   // Constructor vacío
   public Incidente() {
   }

   // Cada vez que cambia el estado, también lo guardo en el historial
   public void agregarCambioEstado(EstadoIncidente nuevoEstado) {

      HistorialEstadoIncidente historial =
              new HistorialEstadoIncidente(this, nuevoEstado);

      // Agrego el nuevo cambio al historial
      historialEstados.add(historial);

      // Actualizo el estado actual del incidente
      this.estado = nuevoEstado;
   }

   // Devuelve el id
   public Long getId() {
      return this.id;
   }

   // Devuelve el título
   public String getTitulo() {
      return this.titulo;
   }

   // Cambia el título
   public void setTitulo(String titulo) {
      this.titulo = titulo;
   }

   // Devuelve la descripción
   public String getDescripcion() {
      return this.descripcion;
   }

   // Cambia la descripción
   public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
   }

   // Devuelve la categoría
   public CategoriaIncidente getCategoria() {
      return this.categoria;
   }

   // Cambia la categoría
   public void setCategoria(CategoriaIncidente categoria) {
      this.categoria = categoria;
   }

   // Devuelve la fecha de alta
   public LocalDateTime getFechaAlta() {
      return this.fechaAlta;
   }

   // Cambia la fecha de alta
   public void setFechaAlta(LocalDateTime fechaAlta) {
      this.fechaAlta = fechaAlta;
   }

   // Devuelve la prioridad
   public PrioridadIncidente getPrioridad() {
      return this.prioridad;
   }

   // Cambia la prioridad
   public void setPrioridad(PrioridadIncidente prioridad) {
      this.prioridad = prioridad;
   }

   // Devuelve el estado
   public EstadoIncidente getEstado() {
      return this.estado;
   }

   // Cambia el estado
   public void setEstado(EstadoIncidente estado) {
      this.estado = estado;
   }

   // Devuelve si está eliminado
   public boolean isEliminado() {
      return this.eliminado;
   }

   // Cambia el estado de eliminado
   public void setEliminado(boolean eliminado) {
      this.eliminado = eliminado;
   }

   // Devuelve la fecha de resolución
   public LocalDateTime getFechaResolucion() {
      return this.fechaResolucion;
   }

   // Cambia la fecha de resolución
   public void setFechaResolucion(LocalDateTime fechaResolucion) {
      this.fechaResolucion = fechaResolucion;
   }

   // Devuelve las observaciones
   public String getObservacionesResolucion() {
      return this.observacionesResolucion;
   }

   // Cambia las observaciones
   public void setObservacionesResolucion(String observacionesResolucion) {
      this.observacionesResolucion = observacionesResolucion;
   }

   // Devuelve el costo de resolución
   public BigDecimal getCostoResolucion() {
      return this.costoResolucion;
   }

   // Cambia el costo
   public void setCostoResolucion(BigDecimal costoResolucion) {
      this.costoResolucion = costoResolucion;
   }

   // Devuelve el técnico responsable
   public String getResponsableTecnico() {
      return this.responsableTecnico;
   }

   // Cambia el técnico responsable
   public void setResponsableTecnico(String responsableTecnico) {
      this.responsableTecnico = responsableTecnico;
   }

   public Propiedad getPropiedad() {
      return this.propiedad;
   }

   public void setPropiedad(Propiedad propiedad) {
      this.propiedad = propiedad;
   }

   // Devuelve el historial de estados
   public List<HistorialEstadoIncidente> getHistorialEstados() {
      return this.historialEstados;
   }
}
