package com.desi.inmobiliaria.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.desi.inmobiliaria.entity.Factura;
import com.desi.inmobiliaria.entity.EstadoFactura;
import com.desi.inmobiliaria.service.FacturaService;
import com.desi.inmobiliaria.service.ContratoService;
import com.desi.inmobiliaria.service.PropiedadService;
import com.desi.inmobiliaria.service.PersonaService;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/facturas")
public class FacturasBuscarController {

	@Autowired
	private FacturaService facturaService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private PropiedadService propiedadService;

	@Autowired
	private PersonaService personaService;

	@RequestMapping(method = RequestMethod.GET)
	public String preparaForm(Model modelo) {
		FacturasBuscarForm form = new FacturasBuscarForm();
		modelo.addAttribute("formBean", form);
		modelo.addAttribute("facturas", facturaService.getAll());
		cargarCombosFiltro(modelo);
		return "listar";
	}

	@ModelAttribute("estados")
	public EstadoFactura[] getAllEstados() {
		return EstadoFactura.values();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(FacturasBuscarForm formBean, BindingResult result, ModelMap modelo,
			@RequestParam String action) {

		if (action.equals("Buscar")) {
			try {
				List<Factura> facturasActivas = facturaService.buscarConFiltros(formBean.getContratoId(),
						formBean.getInquilinoId(), formBean.getPropiedadId(), formBean.getEstado(),
						formBean.getFechaDesde(), formBean.getFechaHasta());
				modelo.addAttribute("facturas", facturasActivas);
			} catch (Exception e) {
				modelo.addAttribute("error", "Error al filtrar: " + e.getMessage());
				modelo.addAttribute("facturas", List.of());
			}

			cargarCombosFiltro(modelo);
			modelo.addAttribute("formBean", formBean);
			return "listar";
		}

		if (action.equals("Cancelar")) {
			modelo.clear();
			return "redirect:/";
		}

		if (action.equals("Registrar")) {
			modelo.clear();
			return "redirect:/facturas/nueva";
		}

		return "redirect:/";
	}

	@GetMapping("/eliminar")
	public String eliminar(@RequestParam("id") Long id, ModelMap modelo) {
		try {
			facturaService.deleteById(id);
			modelo.addAttribute("mensajeExito", "La factura ha sido eliminada correctamente.");
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}
		return "redirect:/facturas";
	}

	private void cargarCombosFiltro(Model modelo) {
		modelo.addAttribute("contratos", contratoService.listarTodos());
		modelo.addAttribute("propiedades", propiedadService.listarTodas());
		modelo.addAttribute("inquilinos", personaService.listarTodas());
	}

	private void cargarCombosFiltro(ModelMap modelo) {
		modelo.addAttribute("contratos", contratoService.listarTodos());
		modelo.addAttribute("propiedades", propiedadService.listarTodas());
		modelo.addAttribute("inquilinos", personaService.listarTodas());
	}
}