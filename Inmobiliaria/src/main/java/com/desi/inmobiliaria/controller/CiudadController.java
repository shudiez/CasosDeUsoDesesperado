package com.desi.inmobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.service.CiudadService;

@Controller
public class CiudadController {

	@Autowired
	private CiudadService ciudadService;

	@GetMapping("/ciudades")
	public String listar(Model model) {

		model.addAttribute("ciudades", ciudadService.listarTodas());

		return "ciudad-list";
	}

	@GetMapping("/ciudad/nueva")
	public String nuevaCiudad(Model model) {

		model.addAttribute("ciudadForm", new CiudadForm());

		return "ciudad-form";
	}

	@PostMapping("/ciudades")
	public String guardar(CiudadForm ciudadForm) {

		Ciudad ciudad = ciudadForm.toPojo();

		ciudadService.guardar(ciudad);

		return "redirect:/ciudades";
	}

	@GetMapping("/ciudad/editar/{id}")
	public String editarCiudad(@PathVariable Long id, Model model) {

		Ciudad ciudad = ciudadService.buscarPorId(id);

		CiudadForm form = CiudadForm.fromPojo(ciudad);

		model.addAttribute("ciudadForm", form);

		return "ciudad-form";
	}

	@GetMapping("/ciudad/eliminar/{id}")
	public String eliminarCiudad(@PathVariable Long id) {

		ciudadService.eliminar(id);

		return "redirect:/ciudades";
	}
}