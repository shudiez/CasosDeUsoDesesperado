package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.inmobiliaria.entity.Propiedad;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {

}