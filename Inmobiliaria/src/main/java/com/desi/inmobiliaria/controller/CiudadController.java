package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.service.CiudadService;
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
public class CiudadController {
   @Autowired
   private CiudadService ciudadService;
   @Autowired
   private ProvinciaService provinciaService;

   public CiudadController() {
   }

   @GetMapping({"/ciudades"})
   public String listar(Model model) {
      model.addAttribute("ciudades", this.ciudadService.listarTodas());
      return "ciudad-list";
   }

   @GetMapping({"/ciudad/nueva"})
   public String nuevaCiudad(Model model) {
      model.addAttribute("ciudadForm", new CiudadForm());
      model.addAttribute("provincias", this.provinciaService.listarTodas());
      return "ciudad-form";
   }

   @PostMapping({"/ciudades"})
   public String guardar(@ModelAttribute("ciudadForm") @Valid CiudadForm ciudadForm, BindingResult result, Model model) {
      if (result.hasErrors()) {
         model.addAttribute("provincias", this.provinciaService.listarTodas());
         return "ciudad-form";
      } else {
         try {
            Ciudad ciudad = ciudadForm.toPojo();
            this.ciudadService.guardar(ciudad);
            return "redirect:/ciudades";
         } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("provincias", this.provinciaService.listarTodas());
            return "ciudad-form";
         }
      }
   }

   @GetMapping({"/ciudad/editar/{id}"})
   public String editarCiudad(@PathVariable Long id, Model model) {
      Ciudad ciudad = this.ciudadService.buscarPorId(id);
      CiudadForm form = CiudadForm.fromPojo(ciudad);
      model.addAttribute("ciudadForm", form);
      model.addAttribute("provincias", this.provinciaService.listarTodas());
      return "ciudad-form";
   }

   @GetMapping({"/ciudad/eliminar/{id}"})
   public String eliminarCiudad(@PathVariable Long id, Model model) {
      try {
         this.ciudadService.eliminar(id);
         return "redirect:/ciudades";
      } catch (RuntimeException e) {
         model.addAttribute("error", e.getMessage());
         model.addAttribute("ciudades", this.ciudadService.listarTodas());
         return "ciudad-list";
      }
   }
}
