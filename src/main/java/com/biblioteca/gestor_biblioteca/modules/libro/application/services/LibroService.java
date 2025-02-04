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

    public List<Libro> obtenerLibros() {
        return libroRepository.obtenerLibros();
    }

    public Optional<Libro> obtenerLibroPorId(Long id) {
        return libroRepository.obtenerLibroPorId(id);
    }

    public Libro guardarLibro(Libro libro) {
        return libroRepository.guardarActualizarLibro(libro);
    }

    public Libro actualizarLibro(Long id, Libro libro) {

        Libro libroActualizado = libroRepository.obtenerLibroPorId(id).orElseThrow(
                () -> new IllegalArgumentException("Libro con id " + id + " no encontrado"));

        libroActualizado.setTitulo(libro.getTitulo());
        libroActualizado.setAutor(libro.getAutor());
        libroActualizado.setIsbn(libro.getIsbn());
        libroActualizado.setFechaPublicacion(libro.getFechaPublicacion());

        return libroRepository.guardarActualizarLibro(libroActualizado);
    }

    public void borrarLibroPorId(Long id) {
        libroRepository.borrarLibroPorId(id);
    }
}
