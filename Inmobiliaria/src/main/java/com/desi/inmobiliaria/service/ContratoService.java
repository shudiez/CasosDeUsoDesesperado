package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoContrato;
import java.time.LocalDate;
import java.util.List;

public interface ContratoService {
   List<Contrato> listarConFiltros(String propiedad, String inquilino, EstadoContrato estado, LocalDate fechaInicio);

   void guardar(Contrato contrato);

   Contrato buscarPorId(Long id);

   void eliminar(Long id);
   
   List<Contrato> listarTodos();
}
