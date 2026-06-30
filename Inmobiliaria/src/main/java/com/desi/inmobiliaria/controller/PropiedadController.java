package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;
import com.desi.inmobiliaria.service.CiudadService;
import com.desi.inmobiliaria.service.PersonaService;
import com.desi.inmobiliaria.service.PropiedadService;
import com.desi.inmobiliaria.service.ProvinciaService;
import excepciones.Excepcion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PropiedadController {
   @Autowired
   private PropiedadService propiedadService;
   @Autowired
   private CiudadService ciudadService;
   @Autowired
   private ProvinciaService provinciaService;
   @Autowired
   private PersonaService personaService;

   public PropiedadController() {
   }

   @GetMapping({"/propiedad/nueva"})
   public String nuevaPropiedad(Model model) {
      model.addAttribute("propiedadForm", new PropiedadForm());
      model.addAttribute("tipos", TipoPropiedad.values());
      model.addAttribute("estados", EstadoDisponibilidad.values());
      model.addAttribute("ciudades", this.ciudadService.listarTodas());
      model.addAttribute("provincias", this.provinciaService.listarTodas());
      model.addAttribute("propietarios", this.personaService.listarTodas());
      return "propiedad-form";
   }

   @GetMapping({"/propiedad/eliminar/{id}"})
   public String eliminar(@PathVariable Long id, Model model) {
      try {
         this.propiedadService.eliminar(id);
         return "redirect:/propiedades";
      } catch (Excepcion e) {
         model.addAttribute("error", e.getMessage());
         model.addAttribute("propiedades", this.propiedadService.listarTodas());
         model.addAttribute("ciudades", this.ciudadService.listarTodas());
         model.addAttribute("tipos", TipoPropiedad.values());
         model.addAttribute("estados", EstadoDisponibilidad.values());
         return "propiedad-list";
      }
   }

   @GetMapping({"/propiedad/editar/{id}"})
   public String editarPropiedad(@PathVariable Long id, Model model) {
      Propiedad propiedad = this.propiedadService.buscarPorId(id);
      PropiedadForm form = PropiedadForm.fromPojo(propiedad);
      model.addAttribute("propiedadForm", form);
      model.addAttribute("tipos", TipoPropiedad.values());
      model.addAttribute("estados", EstadoDisponibilidad.values());
      model.addAttribute("ciudades", this.ciudadService.listarTodas());
      model.addAttribute("provincias", this.provinciaService.listarTodas());
      model.addAttribute("propietarios", this.personaService.listarTodas());
      return "propiedad-form";
   }

   @GetMapping({"/propiedades"})
   public String listar(Model model) {
      model.addAttribute("propiedades", this.propiedadService.listarTodas());
      model.addAttribute("ciudades", this.ciudadService.listarTodas());
      model.addAttribute("tipos", TipoPropiedad.values());
      model.addAttribute("estados", EstadoDisponibilidad.values());
      return "propiedad-list";
   }

   @PostMapping({"/propiedades"})
   public String guardar(@ModelAttribute("propiedadForm") @Valid PropiedadForm propiedadForm, BindingResult result, Model model) {
      if (result.hasErrors()) {
         model.addAttribute("tipos", TipoPropiedad.values());
         model.addAttribute("estados", EstadoDisponibilidad.values());
         model.addAttribute("ciudades", this.ciudadService.listarTodas());
         model.addAttribute("provincias", this.provinciaService.listarTodas());
         model.addAttribute("propietarios", this.personaService.listarTodas());
         return "propiedad-form";
      } else {
         try {
            Propiedad propiedad = propiedadForm.toPojo();
            this.propiedadService.guardar(propiedad);
            return "redirect:/propiedades";
         } catch (Excepcion e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("propiedadForm", propiedadForm);
            model.addAttribute("tipos", TipoPropiedad.values());
            model.addAttribute("estados", EstadoDisponibilidad.values());
            model.addAttribute("ciudades", this.ciudadService.listarTodas());
            model.addAttribute("provincias", this.provinciaService.listarTodas());
            model.addAttribute("propietarios", this.personaService.listarTodas());
            return "propiedad-form";
         }
      }
   }

   @GetMapping({"/propiedades/buscar"})
   public String buscar(@RequestParam(required = false) String direccion, @RequestParam(required = false) Long ciudadId, @RequestParam(required = false) TipoPropiedad tipo, @RequestParam(required = false) EstadoDisponibilidad estado, Model model) {
      model.addAttribute("propiedades", this.propiedadService.buscarConFiltros(direccion, ciudadId, tipo, estado));
      model.addAttribute("ciudades", this.ciudadService.listarTodas());
      model.addAttribute("tipos", TipoPropiedad.values());
      model.addAttribute("estados", EstadoDisponibilidad.values());
      return "propiedad-list";
   }
}
