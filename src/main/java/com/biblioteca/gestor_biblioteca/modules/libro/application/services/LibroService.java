package com.biblioteca.gestor_biblioteca.modules.libro.application.services;

import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.repository.LibroRepository;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.exceptions.LibroException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;

    public List<Libro> obtenerLibros() {

        log.info("[Service] obteniendo lista de libros");

        return libroRepository.obtenerLibros();
    }

    public Libro obtenerLibroPorId(Long id) throws LibroException {

        log.info("[Service] obteniendo libro con id {}", id);

        return libroRepository.obtenerLibroPorId(id).orElseThrow(() -> {
            log.warn("[Service] no se encontró ningun libro con id {}", id);
            return new LibroException(404,
                    "No hay resultados para el libro con id " + id);
        });

    }

    public Libro guardarLibro(Libro libro) {

        validarLibroAGuardar(libro);

        log.info("[Service] creando libro nuevo");
        return libroRepository.guardarActualizarLibro(libro);

    }

    public Libro actualizarLibro(Long id, Libro libro) {

        validarLibroAGuardar(libro);

        log.info("[Service] actualizando el libro {}", libro.getTitulo());

        Libro libroActualizado = libroRepository.obtenerLibroPorId(id).orElseThrow(
                () -> new LibroException(404,"Libro con id " + id + " no encontrado"));

        libroActualizado.setTitulo(libro.getTitulo());
        libroActualizado.setAutor(libro.getAutor());
        libroActualizado.setIsbn(libro.getIsbn());
        libroActualizado.setFechaPublicacion(libro.getFechaPublicacion());

        return libroRepository.guardarActualizarLibro(libroActualizado);
    }

    public Libro actualizarParcialmenteLibro(Long id, Map<String, String> camposActualizados) {

        log.info("[Service] actualizando el libro con id {}", id);

        Libro libroActualizado = libroRepository.obtenerLibroPorId(id).orElseThrow(
                () -> new LibroException(404,"Libro con id " + id + " no encontrado"));

        camposActualizados.forEach((campo, valor) -> {

            if(valor == null || valor.isBlank()){
                throw new LibroException(400,"El campo " + campo +  " no puede estar vacio");
            }
            switch (campo) {
                case "titulo" -> libroActualizado.setTitulo(valor);
                case "autor" -> libroActualizado.setAutor(valor);
                case "isbn" -> libroActualizado.setIsbn(valor);
                case "fechaPublicacion" ->{
                    try {
                        libroActualizado.setFechaPublicacion(LocalDate.parse(valor));
                    } catch (Exception e) {
                        throw new LibroException(400, "Formato de fecha inválido");
                    }
                }
                default -> throw new LibroException(400, "Campo no válido " + campo);
            }
        });

        return libroRepository.guardarActualizarLibro(libroActualizado);

    }

    public void borrarLibroPorId(Long id) {

        log.info("[Service] eliminando el libro con id {}", id);
        if (libroRepository.obtenerLibroPorId(id).isPresent()) {

            libroRepository.borrarLibroPorId(id);
            log.info("[Service] el libro se ha eliminado");

        } else {
            log.warn("[Service] no se encontró el libro con id {}", id);
            throw new LibroException(404, "No existe ningun libro con id " + id);
        }

    }


    public void validarLibroAGuardar(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {

            throw new LibroException(400, "El titulo del libro no puede estar vacio");
        }
        if (libro.getAutor() == null|| libro.getAutor().isBlank()) {

            throw new LibroException(400, "El autor del libro no puede estar vacio");
        }
        if (libro.getIsbn() == null|| libro.getIsbn().isBlank()) {

            throw new LibroException(400, "El ISBN del libro no puede estar vacio");
        }
        if (libro.getFechaPublicacion() == null) {

            throw new LibroException(400, "La fecha de publicación del libro no puede estar vacio");
        }
    }

}
