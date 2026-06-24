package com.desi.inmobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.desi.inmobiliaria.entity.Factura;
import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.entity.EstadoFactura;
import com.desi.inmobiliaria.entity.MedioPago;
import com.desi.inmobiliaria.controller.FacturaForm;
import com.desi.inmobiliaria.service.FacturaService;
import com.desi.inmobiliaria.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/facturas")
public class FacturasEditarController {

	@Autowired
	private FacturaService facturaService;

	@Autowired
	private ContratoService contratoService; // Necesario para listar los contratos en el alta

	// 4.1 Alta - Muestra el formulario vacío
	@GetMapping("/nueva")
	public String nuevaFactura(Model model) {
		FacturaForm form = new FacturaForm();
		model.addAttribute("facturaForm", form);
		cargarAtributosFormulario(model);
		return "Facturaform"; 
	}

	// 4.2 Modificación - Busca la factura y la precarga en el formulario
	@GetMapping("/editar/{id}")
	public String editarFactura(@PathVariable("id") Long id, Model model) {
		try {
			Factura factura = facturaService.getById(id);
			FacturaForm form = FacturaForm.fromPojo(factura); // Convierte la entidad a Formulario
			model.addAttribute("facturaForm", form);
			cargarAtributosFormulario(model);
			return "Facturaform";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/facturas";
		}
	}

	// Procesa el guardado (tanto para Alta como para Modificación)
	@PostMapping("/guardar")
	public String guardar(@jakarta.validation.Valid @ModelAttribute("facturaForm") FacturaForm facturaForm,
			BindingResult result, Model model) {

		// Si el usuario se olvidó de un dato obligatorio, frena y vuelve al formulario
		// mostrando los errores
		if (result.hasErrors()) {
			// Volvemos a cargar las listas para los desplegables para que no se rompa la
			// pantalla
			cargarAtributosFormulario(model);
			return "Facturaform";
		}

		try {
			if (facturaForm.getId() == null) {
				facturaService.save(facturaForm.toPojo());
			} else {
				facturaService.update(facturaForm.toPojo());
			}
			return "redirect:/facturas";
		} catch (Exception e) {
			model.addAttribute("error", "Error al procesar la factura: " + e.getMessage());
			cargarAtributosFormulario(model);
			return "Facturaform";
		}
	}


	// Método auxiliar para no repetir código de los combos/desplegables
	private void cargarAtributosFormulario(Model model) {
		model.addAttribute("contratos", contratoService.listarConFiltros(null, null, EstadoContrato.ACTIVO, null));
		model.addAttribute("estados", EstadoFactura.values());
		model.addAttribute("mediosPago", MedioPago.values());
	}
}