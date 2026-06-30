package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Provincia;
import java.util.List;

public interface ProvinciaService {

   // Devuelve todas las provincias
   List<Provincia> listarTodas();

   // Busca una provincia por su id
   Provincia buscarPorId(Long id);

   // Guarda una provincia nueva o editada
   Provincia guardar(Provincia provincia);

   // Elimina una provincia
   void eliminar(Long id);
}
