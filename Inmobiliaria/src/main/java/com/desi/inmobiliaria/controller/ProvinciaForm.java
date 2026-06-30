package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Provincia;
import jakarta.validation.constraints.NotBlank;

public class ProvinciaForm {

   // Id de la provincia
   private Long id;

   // El nombre es obligatorio
   @NotBlank(message = "El nombre de la provincia es obligatorio")
   private String nombre;

   // Constructor vacío
   public ProvinciaForm() {
   }

   // Copio los datos de una Provincia al formulario
   // Lo uso cuando quiero editar una provincia
   public ProvinciaForm(Provincia provincia) {
      this.id = provincia.getId();
      this.nombre = provincia.getNombre();
   }

   // Convierte una Provincia en un ProvinciaForm
   // Así puedo mostrar sus datos en el formulario
   public static ProvinciaForm fromPojo(Provincia provincia) {
      return new ProvinciaForm(provincia);
   }

   // Convierte los datos del formulario en una Provincia
   // Después este objeto se manda al service para guardarlo
   public Provincia toPojo() {

      Provincia provincia = new Provincia();

      provincia.setId(this.id);
      provincia.setNombre(this.nombre);

      return provincia;
   }

   // Devuelve el id
   public Long getId() {
      return this.id;
   }

   // Cambia el id
   public void setId(Long id) {
      this.id = id;
   }

   // Devuelve el nombre
   public String getNombre() {
      return this.nombre;
   }

   // Cambia el nombre
   public void setNombre(String nombre) {
      this.nombre = nombre;
   }
}
