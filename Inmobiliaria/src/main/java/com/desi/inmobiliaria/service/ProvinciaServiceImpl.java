package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Provincia;
import com.desi.inmobiliaria.repository.CiudadRepository;
import com.desi.inmobiliaria.repository.ProvinciaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase contiene la lógica de negocio
public class ProvinciaServiceImpl implements ProvinciaService {

    // Repository para trabajar con las provincias
    @Autowired
    private ProvinciaRepository provinciaRepository;

    // Lo uso para verificar si una provincia tiene ciudades asociadas
    @Autowired
    private CiudadRepository ciudadRepository;

    public ProvinciaServiceImpl() {
    }

    // Devuelve todas las provincias
    @Override
    public List<Provincia> listarTodas() {
        return provinciaRepository.findAll();
    }

    // Busca una provincia por su id
    @Override
    public Provincia buscarPorId(Long id) {
        return provinciaRepository.findById(id).orElse(null);
    }

    // Guarda una provincia nueva o editada
    @Override
    public Provincia guardar(Provincia provincia) {

        // Verifico que el nombre no esté vacío
        if (provincia.getNombre() == null || provincia.getNombre().isBlank()) {
            throw new RuntimeException("El nombre de la provincia es obligatorio");
        }

        // Si es una provincia nueva, verifico que no exista otra con el mismo nombre
        if (provincia.getId() == null) {

            if (provinciaRepository.findByNombre(provincia.getNombre()).isPresent()) {
                throw new RuntimeException("Ya existe una provincia con ese nombre");
            }

        } else {

            // Si estoy editando, verifico que otra provincia no tenga ese nombre
            if (provinciaRepository.existsByNombreAndIdNot(
                    provincia.getNombre(),
                    provincia.getId())) {

                throw new RuntimeException("Ya existe una provincia con ese nombre");
            }
        }

        // Si todas las validaciones están bien, la guardo
        return provinciaRepository.save(provincia);
    }

    // Elimina una provincia
    @Override
    public void eliminar(Long id) {

        // Si la provincia tiene ciudades asociadas, no la dejo eliminar
        if (ciudadRepository.existsByProvinciaId(id)) {
            throw new RuntimeException(
                    "No se puede eliminar la provincia porque tiene ciudades asociadas");
        }

        // Si no tiene ciudades, la elimino
        provinciaRepository.deleteById(id);
    }
}
