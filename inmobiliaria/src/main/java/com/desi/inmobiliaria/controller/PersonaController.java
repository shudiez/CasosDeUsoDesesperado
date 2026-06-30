package com.desi.inmobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.desi.inmobiliaria.entity.Persona;
import com.desi.inmobiliaria.service.PersonaService;

import jakarta.validation.Valid;


@Controller
public class PersonaController {
	@Autowired
	private PersonaService personaService;

	// METODO LISTAR PERSONAS
	@GetMapping("/personas")
	public String listar(Model model) {

		model.addAttribute("personas", personaService.listarTodas());

		return "persona-list";
	}

	// METODO CREAR PERSONA
	@GetMapping("/persona/nueva")
	public String nuevaPersona(Model model) {

		model.addAttribute("personaForm", new PersonaForm());

		return "persona-form";
	}

	// METODO GUARDAR PERSONA
	@PostMapping("/personas")
	public String guardar(@Valid @ModelAttribute("personaForm") PersonaForm personaForm, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "persona-form";
		}

		try {

			Persona persona = personaForm.toPojo();

			personaService.guardar(persona);

			return "redirect:/personas";

		} catch (RuntimeException e) {

			model.addAttribute("error", e.getMessage());

			return "persona-form";
		}
	}

	// METODO EDITAR PERSONA
	@GetMapping("/persona/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {

		Persona persona = personaService.buscarPorId(id);

		PersonaForm form = PersonaForm.fromPojo(persona);

		model.addAttribute("personaForm", form);

		return "persona-form";
	}

	// METODO ELIMINAR PERSONA
	@GetMapping("/persona/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model model) {

		try {

			personaService.eliminar(id);

			return "redirect:/personas";

		} catch (RuntimeException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("personas", personaService.listarTodas());

			return "persona-list";
		}
	}
}