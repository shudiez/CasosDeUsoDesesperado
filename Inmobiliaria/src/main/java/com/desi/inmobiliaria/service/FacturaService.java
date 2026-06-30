package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.EstadoFactura;
import com.desi.inmobiliaria.entity.Factura;
import excepciones.Excepcion;
import java.time.LocalDate;
import java.util.List;

public interface FacturaService {
   List<Factura> getAll();

   Factura getById(Long id) throws Excepcion;

   void save(Factura factura) throws Excepcion;

   void update(Factura factura) throws Excepcion;

   void deleteById(Long id) throws Excepcion;

   List<Factura> buscarConFiltros(Long contratoId, Long inquilinoId, Long propiedadId, EstadoFactura estado, LocalDate desde, LocalDate hasta);
}
