package com.biblioteca.gestor_biblioteca.modules.libro.application.services;

import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.repository.LibroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;

    public List<Libro> obtenerLibros(){
        return libroRepository.obtenerLibros();
    }

    public Optional<Libro> obtenerLibroPorId(Long id){
        return libroRepository.obtenerLibroPorId(id);
    }

    public Libro guardarLibro(Libro libro){
        return libroRepository.guardarLibro(libro);
    }
}
