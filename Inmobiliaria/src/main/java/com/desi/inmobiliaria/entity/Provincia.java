package com.desi.inmobiliaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Indica que esta clase se guarda en la base de datos
public class Provincia {

   // Id único de la provincia
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   // Nombre de la provincia
   private String nombre;

   // Constructor vacío
   public Provincia() {
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
