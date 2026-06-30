package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Persona;
import com.desi.inmobiliaria.service.PersonaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonaController {
   @Autowired
   private PersonaService personaService;

   public PersonaController() {
   }

   @GetMapping({"/personas"})
   public String listar(Model model) {
      model.addAttribute("personas", this.personaService.listarTodas());
      return "persona-list";
   }

   @GetMapping({"/persona/nueva"})
   public String nuevaPersona(Model model) {
      model.addAttribute("personaForm", new PersonaForm());
      return "persona-form";
   }

   @PostMapping({"/personas"})
   public String guardar(@ModelAttribute("personaForm") @Valid PersonaForm personaForm, BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "persona-form";
      } else {
         try {
            Persona persona = personaForm.toPojo();
            this.personaService.guardar(persona);
            return "redirect:/personas";
         } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "persona-form";
         }
      }
   }

   @GetMapping({"/persona/editar/{id}"})
   public String editar(@PathVariable Long id, Model model) {
      Persona persona = this.personaService.buscarPorId(id);
      PersonaForm form = PersonaForm.fromPojo(persona);
      model.addAttribute("personaForm", form);
      return "persona-form";
   }

   @GetMapping({"/persona/eliminar/{id}"})
   public String eliminar(@PathVariable Long id, Model model) {
      try {
         this.personaService.eliminar(id);
         return "redirect:/personas";
      } catch (RuntimeException e) {
         model.addAttribute("error", e.getMessage());
         model.addAttribute("personas", this.personaService.listarTodas());
         return "persona-list";
      }
   }
}
