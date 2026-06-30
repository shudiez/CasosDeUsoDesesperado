package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Provincia;
import com.desi.inmobiliaria.service.ProvinciaService;
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
public class ProvinciaController {
   @Autowired
   private ProvinciaService provinciaService;

   public ProvinciaController() {
   }

   @GetMapping({"/provincias"})
   public String listar(Model model) {
      model.addAttribute("provincias", this.provinciaService.listarTodas());
      return "provincia-list";
   }

   @GetMapping({"/provincia/nueva"})
   public String nuevaProvincia(Model model) {
      model.addAttribute("provinciaForm", new ProvinciaForm());
      return "provincia-form";
   }

   @PostMapping({"/provincias"})
   public String guardar(@ModelAttribute("provinciaForm") @Valid ProvinciaForm provinciaForm, BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "provincia-form";
      } else {
         try {
            Provincia provincia = provinciaForm.toPojo();
            this.provinciaService.guardar(provincia);
            return "redirect:/provincias";
         } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("provinciaForm", provinciaForm);
            return "provincia-form";
         }
      }
   }

   @GetMapping({"/provincia/editar/{id}"})
   public String editar(@PathVariable Long id, Model model) {
      Provincia provincia = this.provinciaService.buscarPorId(id);
      ProvinciaForm form = ProvinciaForm.fromPojo(provincia);
      model.addAttribute("provinciaForm", form);
      return "provincia-form";
   }

   @GetMapping({"/provincia/eliminar/{id}"})
   public String eliminar(@PathVariable Long id, Model model) {
      try {
         this.provinciaService.eliminar(id);
         return "redirect:/provincias";
      } catch (RuntimeException e) {
         model.addAttribute("error", e.getMessage());
         model.addAttribute("provincias", this.provinciaService.listarTodas());
         return "provincia-list";
      }
   }
}
