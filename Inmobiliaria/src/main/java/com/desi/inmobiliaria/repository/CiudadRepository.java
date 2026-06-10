package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.inmobiliaria.entity.Ciudad;

//Se usa para guardar, buscar y listar ciudades en la base de datos
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

}
