package com.desi.inmobiliaria.service;


import java.util.List;

import com.desi.entity.Contrato;


public interface ContratoService {

	
	List<Contrato> listarTodos();
    
    void guardar(Contrato contrato);
    
    Contrato buscarPorId(Long id);
    
    void eliminar(Long id);
	
	
}
