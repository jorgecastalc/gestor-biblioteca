package com.biblioteca.gestor_biblioteca.modules.libro.application.services;

import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.repository.LibroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

    public Libro actualizarParcialmenteLibro(Long id, Map<String, String> camposActualizados) {

        Libro libroActualizado = libroRepository.obtenerLibroPorId(id).orElseThrow(
                () -> new IllegalArgumentException("Libro con id " + id + " no encontrado"));

        camposActualizados.forEach((campo, valor) -> {
            switch (campo) {
                case "titulo" -> libroActualizado.setTitulo(valor);
                case "autor" -> libroActualizado.setAutor(valor);
                case "isbn" -> libroActualizado.setIsbn(valor);
                case "fechaPublicacion" ->
                        libroActualizado.setFechaPublicacion(LocalDate.parse(valor));
                default -> throw new IllegalArgumentException("Campo no válido " + campo);
            }
        });

        return libroRepository.guardarActualizarLibro(libroActualizado);

    }

    public void borrarLibroPorId(Long id) {
        libroRepository.borrarLibroPorId(id);
    }
}
