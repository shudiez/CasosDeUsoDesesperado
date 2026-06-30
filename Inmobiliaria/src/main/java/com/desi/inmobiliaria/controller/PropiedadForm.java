package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Persona;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PropiedadForm {

   // Datos que completa el usuario en el formulario
   private Long id;

   @NotBlank(message = "La dirección es obligatoria")
   private String direccion;

   @NotNull(message = "La cantidad de ambientes es obligatoria")
   @Positive(message = "La cantidad de ambientes debe ser mayor a cero")
   private Integer cantidadAmbientes;

   @NotNull(message = "Los metros cuadrados son obligatorios")
   @Positive(message = "Los metros cuadrados deben ser mayores a cero")
   private Double metrosCuadrados;

   @NotBlank(message = "La descripción es obligatoria")
   private String descripcion;

   @NotBlank(message = "Las comodidades son obligatorias")
   private String comodidades;

   @NotNull(message = "Debe seleccionar una ciudad")
   private Long ciudadId;

   @NotNull(message = "Debe seleccionar una provincia")
   private Long provinciaId;

   @NotNull(message = "Debe seleccionar un propietario")
   private Long propietarioId;

   @NotNull(message = "Debe seleccionar un tipo de propiedad")
   private TipoPropiedad tipo;

   private EstadoDisponibilidad estadoDisponibilidad;

   // Constructor vacío
   public PropiedadForm() {
   }

   // Copio los datos de una Propiedad al formulario
   // Lo uso cuando quiero editar una propiedad
   public PropiedadForm(Propiedad p) {

      this.id = p.getId();
      this.direccion = p.getDireccion();
      this.cantidadAmbientes = p.getCantidadAmbientes();
      this.metrosCuadrados = p.getMetrosCuadrados();
      this.descripcion = p.getDescripcion();
      this.comodidades = p.getComodidades();
      this.tipo = p.getTipo();
      this.estadoDisponibilidad = p.getEstadoDisponibilidad();

      // Guardo el id de la ciudad
      if (p.getCiudad() != null) {
         this.ciudadId = p.getCiudad().getId();
      }

      // Guardo el id del propietario
      if (p.getPropietario() != null) {
         this.propietarioId = p.getPropietario().getId();
      }
   }

   // Convierte una Propiedad en un PropiedadForm
   // Así puedo mostrar los datos en el formulario
   public static PropiedadForm fromPojo(Propiedad p) {

      PropiedadForm form = new PropiedadForm();

      form.setId(p.getId());
      form.setDireccion(p.getDireccion());
      form.setCantidadAmbientes(p.getCantidadAmbientes());
      form.setMetrosCuadrados(p.getMetrosCuadrados());
      form.setDescripcion(p.getDescripcion());
      form.setComodidades(p.getComodidades());
      form.setTipo(p.getTipo());
      form.setEstadoDisponibilidad(p.getEstadoDisponibilidad());

      if (p.getCiudad() != null) {
         form.setCiudadId(p.getCiudad().getId());
      }

      if (p.getPropietario() != null) {
         form.setPropietarioId(p.getPropietario().getId());
      }

      return form;
   }

   // Convierte los datos del formulario en una Propiedad
   // Después este objeto se manda al service para guardarlo
   public Propiedad toPojo() {

      Propiedad p = new Propiedad();

      p.setId(this.id);
      p.setDireccion(this.direccion);
      p.setCantidadAmbientes(this.cantidadAmbientes);
      p.setMetrosCuadrados(this.metrosCuadrados);
      p.setDescripcion(this.descripcion);
      p.setComodidades(this.comodidades);
      p.setTipo(this.tipo);
      p.setEstadoDisponibilidad(this.estadoDisponibilidad);

      // Si se eligió una ciudad, la asigno a la propiedad
      if (this.ciudadId != null) {
         Ciudad ciudad = new Ciudad();
         ciudad.setId(this.ciudadId);
         p.setCiudad(ciudad);
      }

      // Si se eligió un propietario, lo asigno a la propiedad
      if (this.propietarioId != null) {
         Persona propietario = new Persona();
         propietario.setId(this.propietarioId);
         p.setPropietario(propietario);
      }

      return p;
   }

   // Devuelve el id
   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   // Devuelve la dirección
   public String getDireccion() {
      return this.direccion;
   }

   public void setDireccion(String direccion) {
      this.direccion = direccion;
   }

   // Devuelve la cantidad de ambientes
   public Integer getCantidadAmbientes() {
      return this.cantidadAmbientes;
   }

   public void setCantidadAmbientes(Integer cantidadAmbientes) {
      this.cantidadAmbientes = cantidadAmbientes;
   }

   // Devuelve los metros cuadrados
   public Double getMetrosCuadrados() {
      return this.metrosCuadrados;
   }

   public void setMetrosCuadrados(Double metrosCuadrados) {
      this.metrosCuadrados = metrosCuadrados;
   }

   // Devuelve la descripción
   public String getDescripcion() {
      return this.descripcion;
   }

   public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
   }

   // Devuelve las comodidades
   public String getComodidades() {
      return this.comodidades;
   }

   public void setComodidades(String comodidades) {
      this.comodidades = comodidades;
   }

   // Devuelve el id de la ciudad
   public Long getCiudadId() {
      return this.ciudadId;
   }

   public void setCiudadId(Long ciudadId) {
      this.ciudadId = ciudadId;
   }

   // Devuelve el id del propietario
   public Long getPropietarioId() {
      return this.propietarioId;
   }

   public void setPropietarioId(Long propietarioId) {
      this.propietarioId = propietarioId;
   }

   // Devuelve el tipo de propiedad
   public TipoPropiedad getTipo() {
      return this.tipo;
   }

   public void setTipo(TipoPropiedad tipo) {
      this.tipo = tipo;
   }

   // Devuelve el estado de la propiedad
   public EstadoDisponibilidad getEstadoDisponibilidad() {
      return this.estadoDisponibilidad;
   }

   public void setEstadoDisponibilidad(EstadoDisponibilidad estadoDisponibilidad) {
      this.estadoDisponibilidad = estadoDisponibilidad;
   }

   // Devuelve el id de la provincia
   public Long getProvinciaId() {
      return this.provinciaId;
   }

   public void setProvinciaId(Long provinciaId) {
      this.provinciaId = provinciaId;
   }
}
