package com.desi.inmobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desi.inmobiliaria.entity.Provincia;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

}