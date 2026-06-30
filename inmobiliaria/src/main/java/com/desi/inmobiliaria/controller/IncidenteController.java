package com.desi.inmobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.desi.inmobiliaria.entity.CategoriaIncidente;
import com.desi.inmobiliaria.entity.EstadoIncidente;
import com.desi.inmobiliaria.entity.Incidente;
import com.desi.inmobiliaria.entity.PrioridadIncidente;
import com.desi.inmobiliaria.service.IncidenteService;
import com.desi.inmobiliaria.service.PropiedadService;

@Controller
public class IncidenteController {
	@Autowired
	private IncidenteService incidenteService;

	@Autowired
	private PropiedadService propiedadService;

	// ALTA DE INCIDENTE
	@GetMapping("/incidente/nuevo")
	public String nuevoIncidente(Model model) {

		model.addAttribute("incidente", new Incidente());
		model.addAttribute("categorias", CategoriaIncidente.values());
		model.addAttribute("prioridades", PrioridadIncidente.values());
		model.addAttribute("estados", EstadoIncidente.values());
		model.addAttribute("propiedades", propiedadService.listarTodas());

		return "incidente-form";
	}

	// GUARDAR INCIDENTE
	@PostMapping("/incidentes")
	public String guardar(Incidente incidente) {

		incidenteService.guardar(incidente);

		return "redirect:/incidentes";
	}

	// LISTAR INCIDENTES
	@GetMapping("/incidentes")
	public String listar(Model model) {

		model.addAttribute("incidentes", incidenteService.listarTodos());

		return "incidente-list";
	}

	// EDITAR INCIDENTE
	@GetMapping("/incidente/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {

		model.addAttribute("incidente", incidenteService.buscarPorId(id));
		model.addAttribute("categorias", CategoriaIncidente.values());
		model.addAttribute("prioridades", PrioridadIncidente.values());
		model.addAttribute("estados", EstadoIncidente.values());
		model.addAttribute("propiedades", propiedadService.listarTodas());

		return "incidente-form";
	}

	// ELIMINAR INCIDENTE
	@GetMapping("/incidente/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {

		incidenteService.eliminar(id);

		return "redirect:/incidentes";
	}

}