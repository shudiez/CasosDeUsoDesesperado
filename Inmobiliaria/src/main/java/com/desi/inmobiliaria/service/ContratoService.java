package com.desi.inmobiliaria.service;


import java.time.LocalDate;
import java.util.List;

import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoContrato;


public interface ContratoService {

	
	List<Contrato> listarConFiltros(String propiedad, String inquilino, EstadoContrato estado,LocalDate fechaInicio);
    
    void guardar(Contrato contrato);
    
    Contrato buscarPorId(Long id);
    
    void eliminar(Long id);
    
    void cambiarEstado(Long contratoId, EstadoContrato nuevoEstado);
	
}
