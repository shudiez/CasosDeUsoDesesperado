package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Incidente;
import java.util.List;

public interface IncidenteService {
   List<Incidente> listarTodos();

   Incidente guardar(Incidente incidente);

   Incidente buscarPorId(Long id);

   void eliminar(Long id);
}
