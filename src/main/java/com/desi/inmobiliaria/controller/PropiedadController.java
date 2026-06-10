package com.desi.inmobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.service.PropiedadService;

@Controller
public class PropiedadController {

	@Autowired
	private PropiedadService propiedadService;

	// NUEVA PROPIEDAD
	@GetMapping("/propiedad/nueva")
	public String nuevaPropiedad(Model model) {

		model.addAttribute("propiedad", new Propiedad());

		return "propiedad-form";
	}

	// GUARDAR PROPIEDAD
	@PostMapping("/propiedades")
	public String guardar(Propiedad propiedad) {

		propiedadService.guardar(propiedad);

		return "redirect:/propiedades";
	}

	// LISTAR PROPIEDADES
	@GetMapping("/propiedades")
	public String listar(Model model) {

		model.addAttribute("propiedades", propiedadService.listarTodas());

		return "propiedad-list";
	}

	// EDITAR PROPIEDAD
	@GetMapping("/propiedad/editar/{id}")
	public String editarPropiedad(@PathVariable Long id, Model model) {

		Propiedad propiedad = propiedadService.buscarPorId(id);

		model.addAttribute("propiedad", propiedad);

		return "propiedad-form";
	}

	// ELIMINAR PROPIEDAD
	@GetMapping("/propiedad/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {

		propiedadService.eliminar(id);

		return "redirect:/propiedades";
	}

}