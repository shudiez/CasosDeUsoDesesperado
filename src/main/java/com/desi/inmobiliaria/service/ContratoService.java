package com.desi.inmobiliria.service;


import java.time.LocalDate;
import java.util.List;

import com.desi.entity.Contrato;
import com.desi.entity.EstadoContrato;


public interface ContratoService {

	
	List<Contrato> listarConFiltros(String propiedad, String inquilino, EstadoContrato estado,LocalDate fechaInicio);
    
    void guardar(Contrato contrato);
    
    Contrato buscarPorId(Long id);
    
    void eliminar(Long id);
    
    void cambiarEstado(Long contratoId, EstadoContrato nuevoEstado);
	
}
