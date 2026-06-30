package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Provincia;
import java.util.List;

public interface ProvinciaService {
   List<Provincia> listarTodas();

   Provincia buscarPorId(Long id);

   Provincia guardar(Provincia provincia);

   void eliminar(Long id);
}
