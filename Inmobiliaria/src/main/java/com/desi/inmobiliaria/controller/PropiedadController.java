package com.desi.inmobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;
import com.desi.inmobiliaria.service.CiudadService;
import com.desi.inmobiliaria.service.PersonaService;
import com.desi.inmobiliaria.service.PropiedadService;

import excepciones.Excepcion;

@Controller
public class PropiedadController {

	@Autowired
	private PropiedadService propiedadService;

	@Autowired
	private CiudadService ciudadService;

	@Autowired
	private PersonaService personaService;

	// ALTA PROPIEDAD
	// Abre el formulario para cargar una nueva propiedad
	@GetMapping("/propiedad/nueva")
	public String nuevaPropiedad(Model model) {

		model.addAttribute("propiedadForm", new PropiedadForm());

		model.addAttribute("tipos", TipoPropiedad.values());
		model.addAttribute("estados", EstadoDisponibilidad.values());
		model.addAttribute("ciudades", ciudadService.listarTodas());
		model.addAttribute("propietarios", personaService.listarTodas());

		return "propiedad-form";
	}

	// EDITAR PROPIEDAD
	// Busca una propiedad por ID y carga sus datos para poder modificarla
	@GetMapping("/propiedad/editar/{id}")
	public String editarPropiedad(@PathVariable Long id, Model model) {

		Propiedad propiedad = propiedadService.buscarPorId(id);

		PropiedadForm form = PropiedadForm.fromPojo(propiedad);

		model.addAttribute("propiedadForm", form);

		model.addAttribute("tipos", TipoPropiedad.values());
		model.addAttribute("estados", EstadoDisponibilidad.values());
		model.addAttribute("ciudades", ciudadService.listarTodas());
		model.addAttribute("propietarios", personaService.listarTodas());

		return "propiedad-form";
	}

	// GUARDAR PROPIEDAD
	// Guarda una nueva propiedad o actualiza una existente
	@PostMapping("/propiedades")
	public String guardar(PropiedadForm propiedadForm, Model model) {

		try {

			Propiedad propiedad = propiedadForm.toPojo();

			propiedadService.guardar(propiedad);

			return "redirect:/propiedades";

		} catch (Excepcion e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("propiedadForm", propiedadForm);

			model.addAttribute("tipos", TipoPropiedad.values());
			model.addAttribute("estados", EstadoDisponibilidad.values());
			model.addAttribute("ciudades", ciudadService.listarTodas());
			model.addAttribute("propietarios", personaService.listarTodas());

			return "propiedad-form";
		}
	}

	// LISTAR PROPIEDADES
	// Muestra todas las propiedades registradas
	@GetMapping("/propiedades")
	public String listar(Model model) {

		model.addAttribute("propiedades", propiedadService.listarTodas());
		model.addAttribute("ciudades", ciudadService.listarTodas());
		model.addAttribute("tipos", TipoPropiedad.values());
		model.addAttribute("estados", EstadoDisponibilidad.values());

		return "propiedad-list";
	}

	// BUSCAR / FILTRAR PROPIEDADES
	@GetMapping("/propiedades/buscar")
	public String buscar(@RequestParam(required = false) String direccion,
			@RequestParam(required = false) Long ciudadId, @RequestParam(required = false) TipoPropiedad tipo,
			@RequestParam(required = false) EstadoDisponibilidad estado, Model model) {

		if (direccion != null && !direccion.isBlank()) {
			model.addAttribute("propiedades", propiedadService.buscarPorDireccion(direccion));

		} else if (ciudadId != null) {
			model.addAttribute("propiedades", propiedadService.buscarPorCiudad(ciudadId));

		} else if (tipo != null) {
			model.addAttribute("propiedades", propiedadService.buscarPorTipo(tipo));

		} else if (estado != null) {
			model.addAttribute("propiedades", propiedadService.buscarPorEstado(estado));

		} else {
			model.addAttribute("propiedades", propiedadService.listarTodas());
		}

		model.addAttribute("ciudades", ciudadService.listarTodas());
		model.addAttribute("tipos", TipoPropiedad.values());
		model.addAttribute("estados", EstadoDisponibilidad.values());

		return "propiedad-list";
	}

	// ELIMINAR PROPIEDAD
	// Elimina una propiedad según su ID
	@GetMapping("/propiedad/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model model) {

		try {

			propiedadService.eliminar(id);

			return "redirect:/propiedades";

		} catch (Excepcion e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("propiedades", propiedadService.listarTodas());
			model.addAttribute("ciudades", ciudadService.listarTodas());
			model.addAttribute("tipos", TipoPropiedad.values());
			model.addAttribute("estados", EstadoDisponibilidad.values());

			return "propiedad-list";
		}
	}

}