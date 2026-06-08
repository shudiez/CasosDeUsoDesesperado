package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.inmobiliaria.entity.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

}
