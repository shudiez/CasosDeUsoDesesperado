package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.entity.EstadoFactura;
import com.desi.inmobiliaria.entity.Factura;
import com.desi.inmobiliaria.service.ContratoService;
import com.desi.inmobiliaria.service.FacturaService;
import com.desi.inmobiliaria.service.PersonaService;
import com.desi.inmobiliaria.service.PropiedadService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/facturas"})
public class FacturasBuscarController {
   @Autowired
   private FacturaService facturaService;
   @Autowired
   private ContratoService contratoService;
   @Autowired
   private PropiedadService propiedadService;
   @Autowired
   private PersonaService personaService;

   public FacturasBuscarController() {
   }

   @RequestMapping(
      method = {RequestMethod.GET}
   )
   public String preparaForm(Model modelo) {
      FacturasBuscarForm form = new FacturasBuscarForm();
      modelo.addAttribute("formBean", form);
      modelo.addAttribute("facturas", this.facturaService.getAll());
      this.cargarCombosFiltro(modelo);
      return "factura-list";
   }

   @ModelAttribute("estados")
   public EstadoFactura[] getAllEstados() {
      return EstadoFactura.values();
   }

   @RequestMapping(
      method = {RequestMethod.POST}
   )
   public String submit(FacturasBuscarForm formBean, BindingResult result, ModelMap modelo, @RequestParam String action) {
      if (action.equals("Buscar")) {
         try {
            List<Factura> facturasActivas = this.facturaService.buscarConFiltros(formBean.getContratoId(), formBean.getInquilinoId(), formBean.getPropiedadId(), formBean.getEstado(), formBean.getFechaDesde(), formBean.getFechaHasta());
            modelo.addAttribute("facturas", facturasActivas);
         } catch (Exception e) {
            modelo.addAttribute("error", "Error al filtrar: " + e.getMessage());
            modelo.addAttribute("facturas", List.of());
         }

         this.cargarCombosFiltro(modelo);
         modelo.addAttribute("formBean", formBean);
         return "factura-list";
      } else if (action.equals("Cancelar")) {
         modelo.clear();
         return "redirect:/";
      } else if (action.equals("Registrar")) {
         modelo.clear();
         return "redirect:/facturas/nueva";
      } else {
         return "redirect:/";
      }
   }

   @GetMapping({"/eliminar"})
   public String eliminar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
      try {
         this.facturaService.deleteById(id);
         redirectAttributes.addFlashAttribute("mensajeExito", "La factura ha sido eliminada correctamente.");
      } catch (Exception e) {
         redirectAttributes.addFlashAttribute("error", e.getMessage());
      }

      return "redirect:/facturas";
   }

   private void cargarCombosFiltro(Model modelo) {
      modelo.addAttribute("contratos", this.contratoService.listarConFiltros((String)null, (String)null, EstadoContrato.ACTIVO, (LocalDate)null));
      modelo.addAttribute("propiedades", this.propiedadService.listarTodas());
      modelo.addAttribute("inquilinos", this.personaService.listarTodas());
   }

   private void cargarCombosFiltro(ModelMap modelo) {
      modelo.addAttribute("contratos", this.contratoService.listarConFiltros((String)null, (String)null, EstadoContrato.ACTIVO, (LocalDate)null));
      modelo.addAttribute("propiedades", this.propiedadService.listarTodas());
      modelo.addAttribute("inquilinos", this.personaService.listarTodas());
   }
}
