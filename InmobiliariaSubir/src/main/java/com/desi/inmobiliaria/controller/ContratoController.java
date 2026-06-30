package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.service.ContratoService;
import com.desi.inmobiliaria.service.PersonaService;
import com.desi.inmobiliaria.service.PropiedadService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContratoController {
   private final ContratoService contratoService;
   private final PropiedadService propiedadService;
   private final PersonaService personaService;

   public ContratoController(ContratoService contratoService, PropiedadService propiedadService, PersonaService personaService) {
      this.contratoService = contratoService;
      this.propiedadService = propiedadService;
      this.personaService = personaService;
   }

   @GetMapping({"/contrato/nuevo"})
   public String nuevoContrato(Model model) {
      Contrato contrato = new Contrato();
      model.addAttribute("contrato", contrato);
      model.addAttribute("propiedades", this.propiedadService.listarTodas());
      model.addAttribute("inquilinos", this.personaService.listarTodas());
      model.addAttribute("estados", EstadoContrato.values());
      return "contrato-form";
   }

   @PostMapping({"/contratos"})
   public String guardar(@ModelAttribute("contrato") @Valid Contrato contrato, BindingResult result, Model model) {
      if (result.hasErrors()) {
         model.addAttribute("propiedades", this.propiedadService.listarTodas());
         model.addAttribute("inquilinos", this.personaService.listarTodas());
         model.addAttribute("estados", EstadoContrato.values());
         return "contrato-form";
      } else {
         if (contrato.getId() == null && contrato.getEstado() != null) {
            contrato.agregarCambioEstado(contrato.getEstado());
         }

         try {
            this.contratoService.guardar(contrato);
            return "redirect:/contratos";
         } catch (IllegalArgumentException e) {
            result.rejectValue("estado", "error.contrato", e.getMessage());
            model.addAttribute("propiedades", this.propiedadService.listarTodas());
            model.addAttribute("inquilinos", this.personaService.listarTodas());
            model.addAttribute("estados", EstadoContrato.values());
            return "contrato-form";
         } catch (RuntimeException e) {
            result.rejectValue("propiedad", "error.propiedad", e.getMessage());
            model.addAttribute("propiedades", this.propiedadService.listarTodas());
            model.addAttribute("inquilinos", this.personaService.listarTodas());
            model.addAttribute("estados", EstadoContrato.values());
            return "contrato-form";
         }
      }
   }

   @GetMapping({"/contratos"})
   public String listarContratos(@RequestParam(value = "propiedad",required = false) String propiedad, @RequestParam(value = "inquilino",required = false) String inquilino, @RequestParam(value = "estado",required = false) EstadoContrato estado, @RequestParam(value = "fechaInicio",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio, Model model) {
      List<Contrato> listaFiltrada = this.contratoService.listarConFiltros(propiedad, inquilino, estado, fechaInicio);
      model.addAttribute("contratos", listaFiltrada);
      model.addAttribute("estados", EstadoContrato.values());
      model.addAttribute("propiedadBuscada", propiedad);
      model.addAttribute("inquilinoBuscado", inquilino);
      model.addAttribute("estadoBuscado", estado);
      model.addAttribute("fechaBuscada", fechaInicio);
      return "contrato-list";
   }

   @GetMapping({"/contrato/editar/{id}"})
   public String editarContrato(@PathVariable Long id, Model model) {
      Contrato contrato = this.contratoService.buscarPorId(id);
      model.addAttribute("contrato", contrato);
      model.addAttribute("propiedades", this.propiedadService.listarTodas());
      model.addAttribute("inquilinos", this.personaService.listarTodas());
      model.addAttribute("estados", EstadoContrato.values());
      return "contrato-form";
   }

   @GetMapping({"/contrato/eliminar/{id}"})
   public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
      try {
         this.contratoService.eliminar(id);
         flash.addFlashAttribute("success", "El contrato se eliminó correctamente.");
      } catch (IllegalArgumentException e) {
         System.out.println("\n log controlador: detuvo el borrado " + e.getMessage());
         flash.addFlashAttribute("error", e.getMessage());
      }

      return "redirect:/contratos";
   }
}
