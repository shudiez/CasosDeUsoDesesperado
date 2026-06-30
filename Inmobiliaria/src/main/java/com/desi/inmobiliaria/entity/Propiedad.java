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

@Entity // Indica que esta clase se guarda en la base de datos
@Table(name = "propiedad") // Nombre de la tabla en la base de datos
public class Propiedad {

   // Id único de la propiedad
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   // La dirección es obligatoria
   @Size(min = 1, max = 200, message = "La dirección es obligatoria")
   private String direccion;

   // Cantidad de ambientes
   private Integer cantidadAmbientes;

   // Metros cuadrados
   private Double metrosCuadrados;

   // Descripción de la propiedad
   @Size(min = 1, max = 500, message = "La descripción es obligatoria")
   private String descripcion;

   // Comodidades que tiene la propiedad
   private String comodidades;

   // Indica si la propiedad fue eliminada de forma lógica
   private boolean eliminada = false;

   // Cada propiedad tiene un solo propietario
   @ManyToOne(optional = false)
   private Persona propietario;

   // Cada propiedad pertenece a una ciudad
   @ManyToOne(optional = false)
   private Ciudad ciudad;

   // Tipo de propiedad (Casa, Departamento, etc.)
   @Enumerated(EnumType.STRING)
   @NotNull(message = "Debe seleccionar un tipo de propiedad")
   private TipoPropiedad tipo;

   // Estado actual de la propiedad
   @Enumerated(EnumType.STRING)
   private EstadoDisponibilidad estadoDisponibilidad;

   // Constructor vacío
   public Propiedad() {
   }

   // Devuelve el id
   public Long getId() {
      return this.id;
   }

   // Cambia el id
   public void setId(Long id) {
      this.id = id;
   }

   // Devuelve la descripción
   public String getDescripcion() {
      return this.descripcion;
   }

   // Cambia la descripción
   public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
   }

   // Devuelve la dirección
   public String getDireccion() {
      return this.direccion;
   }

   // Cambia la dirección
   public void setDireccion(String direccion) {
      this.direccion = direccion;
   }

   // Devuelve la cantidad de ambientes
   public Integer getCantidadAmbientes() {
      return this.cantidadAmbientes;
   }

   // Cambia la cantidad de ambientes
   public void setCantidadAmbientes(Integer cantidadAmbientes) {
      this.cantidadAmbientes = cantidadAmbientes;
   }

   // Devuelve los metros cuadrados
   public Double getMetrosCuadrados() {
      return this.metrosCuadrados;
   }

   // Cambia los metros cuadrados
   public void setMetrosCuadrados(Double metrosCuadrados) {
      this.metrosCuadrados = metrosCuadrados;
   }

   // Devuelve las comodidades
   public String getComodidades() {
      return this.comodidades;
   }

   // Cambia las comodidades
   public void setComodidades(String comodidades) {
      this.comodidades = comodidades;
   }

   // Devuelve si la propiedad está eliminada
   public boolean isEliminada() {
      return this.eliminada;
   }

   // Cambia el estado de eliminación
   public void setEliminada(boolean eliminada) {
      this.eliminada = eliminada;
   }

   // Devuelve el propietario
   public Persona getPropietario() {
      return this.propietario;
   }

   // Cambia el propietario
   public void setPropietario(Persona propietario) {
      this.propietario = propietario;
   }

   // Devuelve la ciudad
   public Ciudad getCiudad() {
      return this.ciudad;
   }

   // Cambia la ciudad
   public void setCiudad(Ciudad ciudad) {
      this.ciudad = ciudad;
   }

   // Devuelve el tipo de propiedad
   public TipoPropiedad getTipo() {
      return this.tipo;
   }

   // Cambia el tipo de propiedad
   public void setTipo(TipoPropiedad tipo) {
      this.tipo = tipo;
   }

   // Devuelve el estado actual
   public EstadoDisponibilidad getEstadoDisponibilidad() {
      return this.estadoDisponibilidad;
   }

   // Cambia el estado
   public void setEstadoDisponibilidad(EstadoDisponibilidad estadoDisponibilidad) {
      this.estadoDisponibilidad = estadoDisponibilidad;
   }
}
