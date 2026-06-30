package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Incidente;
import java.util.List;

public interface IncidenteService {

   // Devuelve todos los incidentes
   List<Incidente> listarTodos();

   // Guarda un incidente nuevo o editado
   Incidente guardar(Incidente incidente);

   // Busca un incidente por su id
   Incidente buscarPorId(Long id);

   // Elimina un incidente
   void eliminar(Long id);
}
