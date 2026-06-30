package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.entity.EstadoFactura;
import com.desi.inmobiliaria.entity.Factura;
import com.desi.inmobiliaria.entity.MedioPago;
import com.desi.inmobiliaria.service.ContratoService;
import com.desi.inmobiliaria.service.FacturaService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/facturas"})
public class FacturasEditarController {
   @Autowired
   private FacturaService facturaService;
   @Autowired
   private ContratoService contratoService;

   public FacturasEditarController() {
   }

   @GetMapping({"/nueva"})
   public String nuevaFactura(Model model) {
      FacturaForm form = new FacturaForm();
      model.addAttribute("facturaForm", form);
      this.cargarAtributosFormulario(model);
      return "factura-form";
   }

   @GetMapping({"/editar/{id}"})
   public String editarFactura(@PathVariable("id") Long id, Model model) {
      try {
         Factura factura = this.facturaService.getById(id);
         FacturaForm form = FacturaForm.fromPojo(factura);
         model.addAttribute("facturaForm", form);
         this.cargarAtributosFormulario(model);
         return "factura-form";
      } catch (Exception e) {
         model.addAttribute("error", e.getMessage());
         return "redirect:/facturas";
      }
   }

   @PostMapping({"/guardar"})
   public String guardar(@ModelAttribute("facturaForm") @Valid FacturaForm facturaForm, BindingResult result, Model model) {
      if (result.hasErrors()) {
         this.cargarAtributosFormulario(model);
         return "factura-form";
      } else {
         try {
            if (facturaForm.getId() == null) {
               this.facturaService.save(facturaForm.toPojo());
            } else {
               this.facturaService.update(facturaForm.toPojo());
            }

            return "redirect:/facturas";
         } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la factura: " + e.getMessage());
            this.cargarAtributosFormulario(model);
            return "factura-form";
         }
      }
   }

   private void cargarAtributosFormulario(Model model) {
      model.addAttribute("contratos", this.contratoService.listarConFiltros((String)null, (String)null, EstadoContrato.ACTIVO, (LocalDate)null));
      model.addAttribute("estados", EstadoFactura.values());
      model.addAttribute("mediosPago", MedioPago.values());
   }
}
