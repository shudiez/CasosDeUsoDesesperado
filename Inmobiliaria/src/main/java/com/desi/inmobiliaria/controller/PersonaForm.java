package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Persona;
import jakarta.validation.constraints.NotBlank;

public class PersonaForm {
   private Long id;
   private @NotBlank(
   message = "El nombre es obligatorio"
) String nombre;
   private @NotBlank(
   message = "El apellido es obligatorio"
) String apellido;
   private @NotBlank(
   message = "El DNI/CUIT es obligatorio"
) String dniCuit;
   private String telefono;
   private String email;
   private String domicilio;

   public PersonaForm() {
   }

   public PersonaForm(Persona p) {
      this.id = p.getId();
      this.nombre = p.getNombre();
      this.apellido = p.getApellido();
      this.dniCuit = p.getDniCuit();
      this.telefono = p.getTelefono();
      this.email = p.getEmail();
      this.domicilio = p.getDomicilio();
   }

   public static PersonaForm fromPojo(Persona p) {
      return new PersonaForm(p);
   }

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

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getNombre() {
      return this.nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public String getApellido() {
      return this.apellido;
   }

   public void setApellido(String apellido) {
      this.apellido = apellido;
   }

   public String getDniCuit() {
      return this.dniCuit;
   }

   public void setDniCuit(String dniCuit) {
      this.dniCuit = dniCuit;
   }

   public String getTelefono() {
      return this.telefono;
   }

   public void setTelefono(String telefono) {
      this.telefono = telefono;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getDomicilio() {
      return this.domicilio;
   }

   public void setDomicilio(String domicilio) {
      this.domicilio = domicilio;
   }
}
