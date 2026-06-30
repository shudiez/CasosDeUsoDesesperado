// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Publicacion;

public interface PublicacionService {
   Publicacion crearPublicacion(Publicacion publicacion);

   void eliminarPublicacion(Long id);

   void modificarPublicacion(Long id, Publicacion datosNuevos);
}
