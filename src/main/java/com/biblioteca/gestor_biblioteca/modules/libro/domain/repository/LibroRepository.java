package com.biblioteca.gestor_biblioteca.modules.libro.domain.repository;

import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroRepository {

    List<Libro> obtenerLibros();
    Optional<Libro> obtenerLibroPorId(Long id);
    Libro guardarLibro(Libro libro);
    void borrarLibroPorId(Long id);

}
