package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Persona;
import com.desi.inmobiliaria.repository.PersonaRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase contiene la lógica de negocio
public class PersonaServiceImpl implements PersonaService {

    // Repository para trabajar con las personas
    @Autowired
    private PersonaRepository personaRepository;

    // Lo uso para verificar si una persona tiene propiedades asociadas
    @Autowired
    private PropiedadRepository propiedadRepository;

    public PersonaServiceImpl() {
    }

    // Devuelve todas las personas
    @Override
    public List<Persona> listarTodas() {
        return personaRepository.findAll();
    }

    // Busca una persona por su id
    @Override
    public Persona buscarPorId(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

    // Guarda una persona nueva o editada
    @Override
    public Persona guardar(Persona persona) {

        // Verifico que el nombre no esté vacío
        if (persona.getNombre() == null || persona.getNombre().isBlank()) {
            throw new RuntimeException("El nombre es obligatorio");
        }

        // Verifico que el apellido no esté vacío
        if (persona.getApellido() == null || persona.getApellido().isBlank()) {
            throw new RuntimeException("El apellido es obligatorio");
        }

        // Verifico que el DNI/CUIT no esté vacío
        if (persona.getDniCuit() == null || persona.getDniCuit().isBlank()) {
            throw new RuntimeException("El DNI/CUIT es obligatorio");
        }

        // Si es una persona nueva, verifico que el DNI/CUIT no exista
        if (persona.getId() == null) {

            if (personaRepository.findByDniCuit(persona.getDniCuit()).isPresent()) {
                throw new RuntimeException("Ya existe una persona con ese DNI/CUIT");
            }

        } else {

            // Si estoy editando, verifico que otra persona no tenga ese DNI/CUIT
            if (personaRepository.existsByDniCuitAndIdNot(
                    persona.getDniCuit(),
                    persona.getId())) {

                throw new RuntimeException("Ya existe una persona con ese DNI/CUIT");
            }
        }

        // Si todas las validaciones están bien, guardo la persona
        return personaRepository.save(persona);
    }

    // Elimina una persona
    @Override
    public void eliminar(Long id) {

        // Si la persona es propietaria de alguna propiedad, no la dejo eliminar
        if (propiedadRepository.existsByPropietarioId(id)) {
            throw new RuntimeException(
                    "No se puede eliminar la persona porque está asociada a una propiedad");
        }

        // Si no tiene propiedades asociadas, la elimino
        personaRepository.deleteById(id);
    }
}
