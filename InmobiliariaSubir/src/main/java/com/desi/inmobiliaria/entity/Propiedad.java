package com.desi.inmobiliaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
   name = "propiedad"
)
public class Propiedad {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   private @Size(
   min = 1,
   max = 200,
   message = "La dirección es obligatoria"
) String direccion;
   private Integer cantidadAmbientes;
   private Double metrosCuadrados;
   private @Size(
   min = 1,
   max = 500,
   message = "La descripción es obligatoria"
) String descripcion;
   private String comodidades;
   private boolean eliminada = false;
   @ManyToOne(
      optional = false
   )
   private Persona propietario;
   @ManyToOne(
      optional = false
   )
   private Ciudad ciudad;
   @Enumerated(EnumType.STRING)
   private @NotNull(
   message = "Debe seleccionar un tipo de propiedad"
) TipoPropiedad tipo;
   @Enumerated(EnumType.STRING)
   private EstadoDisponibilidad estadoDisponibilidad;

   public Propiedad() {
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getDescripcion() {
      return this.descripcion;
   }

   public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
   }

   public String getDireccion() {
      return this.direccion;
   }

   public void setDireccion(String direccion) {
      this.direccion = direccion;
   }

   public Integer getCantidadAmbientes() {
      return this.cantidadAmbientes;
   }

   public void setCantidadAmbientes(Integer cantidadAmbientes) {
      this.cantidadAmbientes = cantidadAmbientes;
   }

   public Double getMetrosCuadrados() {
      return this.metrosCuadrados;
   }

   public void setMetrosCuadrados(Double metrosCuadrados) {
      this.metrosCuadrados = metrosCuadrados;
   }

   public String getComodidades() {
      return this.comodidades;
   }

   public void setComodidades(String comodidades) {
      this.comodidades = comodidades;
   }

   public boolean isEliminada() {
      return this.eliminada;
   }

   public void setEliminada(boolean eliminada) {
      this.eliminada = eliminada;
   }

   public Persona getPropietario() {
      return this.propietario;
   }

   public void setPropietario(Persona propietario) {
      this.propietario = propietario;
   }

   public Ciudad getCiudad() {
      return this.ciudad;
   }

   public void setCiudad(Ciudad ciudad) {
      this.ciudad = ciudad;
   }

   public TipoPropiedad getTipo() {
      return this.tipo;
   }

   public void setTipo(TipoPropiedad tipo) {
      this.tipo = tipo;
   }

   public EstadoDisponibilidad getEstadoDisponibilidad() {
      return this.estadoDisponibilidad;
   }

   public void setEstadoDisponibilidad(EstadoDisponibilidad estadoDisponibilidad) {
      this.estadoDisponibilidad = estadoDisponibilidad;
   }
}
