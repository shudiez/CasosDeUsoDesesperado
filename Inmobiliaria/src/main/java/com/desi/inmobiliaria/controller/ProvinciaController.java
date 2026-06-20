package com.desi.inmobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.desi.inmobiliaria.entity.Provincia;
import com.desi.inmobiliaria.service.ProvinciaService;

import jakarta.validation.Valid;

@Controller
public class ProvinciaController {

	@Autowired
	private ProvinciaService provinciaService;

	@GetMapping("/provincias")
	public String listar(Model model) {

		model.addAttribute("provincias", provinciaService.listarTodas());

		return "provincia-list";
	}

	@GetMapping("/provincia/nueva")
	public String nuevaProvincia(Model model) {

		model.addAttribute("provinciaForm", new ProvinciaForm());

		return "provincia-form";
	}

	@PostMapping("/provincias")
	public String guardar(@Valid @ModelAttribute("provinciaForm") ProvinciaForm provinciaForm, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "provincia-form";
		}

		Provincia provincia = provinciaForm.toPojo();

		provinciaService.guardar(provincia);

		return "redirect:/provincias";
	}

	@GetMapping("/provincia/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {

		Provincia provincia = provinciaService.buscarPorId(id);

		ProvinciaForm form = ProvinciaForm.fromPojo(provincia);

		model.addAttribute("provinciaForm", form);

		return "provincia-form";
	}

	@GetMapping("/provincia/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {

		provinciaService.eliminar(id);

		return "redirect:/provincias";
	}
}