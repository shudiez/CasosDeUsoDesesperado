package com.desi.inmobiliria.service;

import java.util.List;

import com.desi.entity.Persona;

public interface PersonaService {

	List<Persona> listarTodas();
    
    void guardar(Persona persona);
    
    Persona buscarPorId(Long id);
    
    void eliminar(Long id);
	
}
