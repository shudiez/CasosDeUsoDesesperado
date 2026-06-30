package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Persona;
import java.util.List;

public interface PersonaService {
   List<Persona> listarTodas();

   Persona buscarPorId(Long id);

   Persona guardar(Persona persona);

   void eliminar(Long id);
}
