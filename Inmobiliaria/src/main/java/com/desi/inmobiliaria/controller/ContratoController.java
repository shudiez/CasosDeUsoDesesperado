package com.desi.inmobiliaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.service.ContratoService;
import com.desi.inmobiliaria.service.PersonaService;
import com.desi.inmobiliaria.service.PropiedadService;

import jakarta.validation.Valid;

@Controller
public class ContratoController {

	// Dependencias declaradas como 'final' (Buenas prácticas)
	private final ContratoService contratoService;
	private final PropiedadService propiedadService;
	private final PersonaService personaService;

	// Inyección por constructor (Sin necesidad de @Autowired)
	public ContratoController(ContratoService contratoService, PropiedadService propiedadService,
			PersonaService personaService) {
		this.contratoService = contratoService;
		this.propiedadService = propiedadService;
		this.personaService = personaService;
	}

	// 1. NUEVO CONTRATO (Muestra el formulario)

	@GetMapping("/contrato/nuevo")
	public String nuevoContrato(Model model) {
		Contrato contrato = new Contrato();

		model.addAttribute("contrato", contrato);
		// Tip Pro: Usamos el servicio de propiedades para que el usuario pueda
		// seleccionar qué propiedad está asociando a este contrato en un desplegable
		model.addAttribute("propiedades", propiedadService.listarTodas());
		model.addAttribute("inquilinos", personaService.listarTodas()); // <-- Necesitás este servicio
		model.addAttribute("estados", EstadoContrato.values()); // Envía las opciones del Enum

		return "contrato-form";
	}

	// 2. GUARDAR CONTRATO
	@PostMapping("/contratos")
	/*
	 * public String guardar(Contrato contrato) { contratoService.guardar(contrato);
	 * return "redirect:/contratos"; }
	 */
	public String guardar(@Valid @ModelAttribute("contrato") Contrato contrato, BindingResult result, Model model) {
		if (result.hasErrors()) {
			// Si hay errores, volvemos a cargar las listas para los desplegables
			model.addAttribute("propiedades", propiedadService.listarTodas());
			model.addAttribute("inquilinos", personaService.listarTodas());
			model.addAttribute("estados", EstadoContrato.values());
			return "contrato-form";
		}
		// Lógica Pro: Si es un contrato nuevo, le agregamos el estado inicial a tu
		// historial
		if (contrato.getId() == null && contrato.getEstado() != null) {
			contrato.agregarCambioEstado(contrato.getEstado());
		}

		contratoService.guardar(contrato);
		return "redirect:/contratos";
	}

	// 3. LISTAR CONTRATOS
	@GetMapping("/contratos")
	public String listar(Model model) {
		model.addAttribute("contratos", contratoService.listarTodos());
		return "contrato-list";
	}

	// 4. EDITAR CONTRATO
	@GetMapping("/contrato/editar/{id}")
	public String editarContrato(@PathVariable Long id, Model model) {
		Contrato contrato = contratoService.buscarPorId(id);
		model.addAttribute("contrato", contrato);

		// También pasamos las propiedades por si quiere cambiar la propiedad del
		// contrato
		model.addAttribute("propiedades", propiedadService.listarTodas());

		return "contrato-form";
	}

	// 5. ELIMINAR CONTRATO
	@GetMapping("/contrato/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		contratoService.eliminar(id);
		return "redirect:/contratos";
	}
}