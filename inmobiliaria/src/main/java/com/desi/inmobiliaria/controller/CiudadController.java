package com.desi.inmobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.service.CiudadService;
import com.desi.inmobiliaria.service.ProvinciaService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller

public class CiudadController {
	@Autowired
	private CiudadService ciudadService;

	@Autowired
	private ProvinciaService provinciaService;

	// LISTAR CIUDADES
	@GetMapping("/ciudades")
	public String listar(Model model) {

		model.addAttribute("ciudades", ciudadService.listarTodas());

		return "ciudad-list";
	}

	// CARGAR CIUDAD
	@GetMapping("/ciudad/nueva")
	public String nuevaCiudad(Model model) {

		model.addAttribute("ciudadForm", new CiudadForm());
		model.addAttribute("provincias", provinciaService.listarTodas());

		return "ciudad-form";
	}

	// GUARDAR CIUDADES
	@PostMapping("/ciudades")
	public String guardar(@Valid @ModelAttribute("ciudadForm") CiudadForm ciudadForm, BindingResult result,
			Model model) {

		if (result.hasErrors()) {

			model.addAttribute("provincias", provinciaService.listarTodas());

			return "ciudad-form";
		}

		try {

			Ciudad ciudad = ciudadForm.toPojo();

			ciudadService.guardar(ciudad);

			return "redirect:/ciudades";

		} catch (RuntimeException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("provincias", provinciaService.listarTodas());

			return "ciudad-form";
		}
	}

	// EDITAR CIUDADES
	@GetMapping("/ciudad/editar/{id}")
	public String editarCiudad(@PathVariable Long id, Model model) {

		Ciudad ciudad = ciudadService.buscarPorId(id);

		CiudadForm form = CiudadForm.fromPojo(ciudad);

		model.addAttribute("ciudadForm", form);
		model.addAttribute("provincias", provinciaService.listarTodas());

		return "ciudad-form";
	}

	// ELIMINAR CIUDAD
	@GetMapping("/ciudad/eliminar/{id}")
	public String eliminarCiudad(@PathVariable Long id, Model model) {

		try {

			ciudadService.eliminar(id);

			return "redirect:/ciudades";

		} catch (RuntimeException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("ciudades", ciudadService.listarTodas());

			return "ciudad-list";
		}
	}
}