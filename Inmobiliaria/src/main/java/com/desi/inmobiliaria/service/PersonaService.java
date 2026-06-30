package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Persona;
import java.util.List;

public interface PersonaService {

   // Devuelve todas las personas
   List<Persona> listarTodas();

   // Busca una persona por su id
   Persona buscarPorId(Long id);

   // Guarda una persona nueva o editada
   Persona guardar(Persona persona);

   // Elimina una persona
   void eliminar(Long id);
}
