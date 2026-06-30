package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Provincia;
import com.desi.inmobiliaria.repository.CiudadRepository;
import com.desi.inmobiliaria.repository.ProvinciaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinciaServiceImpl implements ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    public ProvinciaServiceImpl() {
    }

    @Override
    public List<Provincia> listarTodas() {
        return provinciaRepository.findAll();
    }

    @Override
    public Provincia buscarPorId(Long id) {
        return provinciaRepository.findById(id).orElse(null);
    }

    @Override
    public Provincia guardar(Provincia provincia) {

        if (provincia.getNombre() == null || provincia.getNombre().isBlank()) {
            throw new RuntimeException("El nombre de la provincia es obligatorio");
        }

        if (provincia.getId() == null) {

            if (provinciaRepository.findByNombre(provincia.getNombre()).isPresent()) {
                throw new RuntimeException("Ya existe una provincia con ese nombre");
            }

        } else {

            if (provinciaRepository.existsByNombreAndIdNot(
                    provincia.getNombre(),
                    provincia.getId())) {

                throw new RuntimeException("Ya existe una provincia con ese nombre");
            }
        }

        return provinciaRepository.save(provincia);
    }

    @Override
    public void eliminar(Long id) {

        if (ciudadRepository.existsByProvinciaId(id)) {
            throw new RuntimeException(
                    "No se puede eliminar la provincia porque tiene ciudades asociadas");
        }

        provinciaRepository.deleteById(id);
    }
}