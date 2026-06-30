package com.desi.inmobiliaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Indica que esta clase se guarda en la base de datos
public class Persona {

   // Id único de la persona
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   // Datos de la persona
   private String nombre;
   private String apellido;
   private String dniCuit;
   private String telefono;
   private String email;
   private String domicilio;

   // Constructor vacío
   public Persona() {
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

   // Devuelve el apellido
   public String getApellido() {
      return this.apellido;
   }

   // Cambia el apellido
   public void setApellido(String apellido) {
      this.apellido = apellido;
   }

   // Devuelve el DNI o CUIT
   public String getDniCuit() {
      return this.dniCuit;
   }

   // Cambia el DNI o CUIT
   public void setDniCuit(String dniCuit) {
      this.dniCuit = dniCuit;
   }

   // Devuelve el teléfono
   public String getTelefono() {
      return this.telefono;
   }

   // Cambia el teléfono
   public void setTelefono(String telefono) {
      this.telefono = telefono;
   }

   // Devuelve el email
   public String getEmail() {
      return this.email;
   }

   // Cambia el email
   public void setEmail(String email) {
      this.email = email;
   }

   // Devuelve el domicilio
   public String getDomicilio() {
      return this.domicilio;
   }

   // Cambia el domicilio
   public void setDomicilio(String domicilio) {
      this.domicilio = domicilio;
   }
}
