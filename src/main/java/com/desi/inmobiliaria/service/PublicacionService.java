package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Publicacion;

public interface PublicacionService {

    Publicacion crearPublicacion(Publicacion publicacion);

    void eliminarPublicacion(Long id);

    void modificarPublicacion(Long id, Publicacion datosNuevos);

}