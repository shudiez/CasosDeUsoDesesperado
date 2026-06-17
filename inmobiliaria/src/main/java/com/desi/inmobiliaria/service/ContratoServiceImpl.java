package com.desi.inmobiliaria.service;

import java.util.List;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.repository.ContratoRepository;

@Service
public class ContratoServiceImpl implements ContratoService {

	// Dependencia del repositorio (Base de datos)
	private final ContratoRepository contratoRepository;

	// Inyección por constructor
	public ContratoServiceImpl(ContratoRepository contratoRepository) {
		this.contratoRepository = contratoRepository;
	}

	@Override
	public List<Contrato> listarTodos() {
		// .findAll() viene heredado de JpaRepository
		return contratoRepository.findAll();
	}

	@Override
	public void guardar(Contrato contrato) {
		// .save() sirve tanto para insertar uno nuevo como para actualizar
		contratoRepository.save(contrato);
	}

	@Override
	public Contrato buscarPorId(Long id) {
		// .findById() devuelve un Optional. Si no lo encuentra, lanzamos una excepción
		return contratoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Contrato no encontrado con el ID: " + id));
	}

	@Override
	public void eliminar(Long id) {
		// .deleteById() elimina el registro de la base de datos
		contratoRepository.deleteById(id);
	}

	// --- MÉTODOS AGREGADOS TEMPORALMENTE PARA QUE COMPILE EL PROYECTO ---

	@Override
	public void cambiarEstado(Long id, EstadoContrato nuevoEstado) {
		// Queda vacío por ahora hasta que tu compañero programe la lógica interna
	}

	@Override
	public List<Contrato> listarConFiltros(String param1, String param2, EstadoContrato estado, LocalDate fecha) {
		// Retorna null temporalmente para cumplir con la interfaz y que no tire error
		return null;
	}
}