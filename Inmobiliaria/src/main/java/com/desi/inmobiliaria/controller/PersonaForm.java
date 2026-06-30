package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Persona;
import jakarta.validation.constraints.NotBlank;

public class PersonaForm {

   // Id de la persona
   private Long id;

   // El nombre es obligatorio
   @NotBlank(message = "El nombre es obligatorio")
   private String nombre;

   // El apellido es obligatorio
   @NotBlank(message = "El apellido es obligatorio")
   private String apellido;

   // El DNI o CUIT es obligatorio
   @NotBlank(message = "El DNI/CUIT es obligatorio")
   private String dniCuit;

   // Datos opcionales
   private String telefono;
   private String email;
   private String domicilio;

   // Constructor vacío
   public PersonaForm() {
   }

   // Copio los datos de una Persona al formulario
   // Lo uso cuando quiero editar una persona
   public PersonaForm(Persona p) {
      this.id = p.getId();
      this.nombre = p.getNombre();
      this.apellido = p.getApellido();
      this.dniCuit = p.getDniCuit();
      this.telefono = p.getTelefono();
      this.email = p.getEmail();
      this.domicilio = p.getDomicilio();
   }

   // Convierte una Persona en un PersonaForm
   // Así puedo mostrar sus datos en el formulario
   public static PersonaForm fromPojo(Persona p) {
      return new PersonaForm(p);
   }

   // Convierte los datos del formulario en una Persona
   // Después este objeto se manda al service para guardarlo
   public Persona toPojo() {

      Persona persona = new Persona();

      persona.setId(this.id);
      persona.setNombre(this.nombre);
      persona.setApellido(this.apellido);
      persona.setDniCuit(this.dniCuit);
      persona.setTelefono(this.telefono);
      persona.setEmail(this.email);
      persona.setDomicilio(this.domicilio);

      return persona;
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

   // Devuelve el DNI/CUIT
   public String getDniCuit() {
      return this.dniCuit;
   }

   // Cambia el DNI/CUIT
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
