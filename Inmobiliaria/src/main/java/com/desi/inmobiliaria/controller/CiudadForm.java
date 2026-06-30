package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.entity.Provincia;
import jakarta.validation.constraints.NotBlank;

public class CiudadForm {

   // Guarda el id de la ciudad
   private Long id;

   // El nombre no puede estar vacío
   @NotBlank(message = "El nombre de la ciudad es obligatorio")
   private String nombre;

   // Guarda el id de la provincia seleccionada
   private Long provinciaId;

   // Constructor vacío
   public CiudadForm() {
   }

   // Constructor que copia los datos de una Ciudad al formulario
   public CiudadForm(Ciudad c) {
      this.id = c.getId();
      this.nombre = c.getNombre();

      // Si la ciudad tiene provincia, guardo su id
      if (c.getProvincia() != null) {
         this.provinciaId = c.getProvincia().getId();
      }
   }

   // Convierte un objeto Ciudad en un CiudadForm
   // Lo uso cuando quiero editar una ciudad
   public static CiudadForm fromPojo(Ciudad c) {

      CiudadForm form = new CiudadForm();

      form.setId(c.getId());
      form.setNombre(c.getNombre());

      if (c.getProvincia() != null) {
         form.setProvinciaId(c.getProvincia().getId());
      }

      return form;
   }

   // Convierte el formulario nuevamente en una entidad Ciudad
   // Lo uso antes de guardar en la base de datos
   public Ciudad toPojo() {

      Ciudad ciudad = new Ciudad();

      ciudad.setId(this.id);
      ciudad.setNombre(this.nombre);

      // Si se eligió una provincia, la asigno a la ciudad
      if (this.provinciaId != null) {
         Provincia provincia = new Provincia();
         provincia.setId(this.provinciaId);
         ciudad.setProvincia(provincia);
      }

      return ciudad;
   }

   // Devuelve el id
   public Long getId() {
      return this.id;
   }

   // Modifica el id
   public void setId(Long id) {
      this.id = id;
   }

   // Devuelve el nombre
   public String getNombre() {
      return this.nombre;
   }

   // Modifica el nombre
   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   // Devuelve el id de la provincia
   public Long getProvinciaId() {
      return this.provinciaId;
   }

   // Modifica el id de la provincia
   public void setProvinciaId(Long provinciaId) {
      this.provinciaId = provinciaId;
   }
}
