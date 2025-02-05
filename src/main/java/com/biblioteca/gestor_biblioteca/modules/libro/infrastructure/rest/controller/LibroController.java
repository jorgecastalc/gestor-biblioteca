package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.controller;


import com.biblioteca.gestor_biblioteca.modules.libro.application.services.LibroService;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.mapper.LibroMapper;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.exceptions.LibroException;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.requests.LibroRequest;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.responses.LibroResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/libros")
@AllArgsConstructor
public class LibroController {

    private final LibroService libroService;
    private final LibroMapper libroMapper;

    @GetMapping
    public ResponseEntity<List<LibroResponse>> obtenerLibros() {

        log.debug("[Controller] obteniendo lista de libros...");
        List<Libro> librosList = libroService.obtenerLibros();

        log.info("[Controller] se han obtenido {} resultados", librosList.size());

        List<LibroResponse> libroResponseList = libroMapper.libroListToLibroResponseList(
                librosList);

        return ResponseEntity.ok(libroResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerLibroPorId(@PathVariable Long id) {

        log.debug("[Controller] obteniendo libro con id {} ", id);

        try {
            Libro libro = libroService.obtenerLibroPorId(id);
            return ResponseEntity.ok().body(libroMapper.libroDomainToLibroResponse(libro));

        } catch (LibroException e) {
            log.error("[Controller] Error al obtener el libro con id {} ", id, e.getMessage(), e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<Object> crearLibro(@RequestBody LibroRequest libroRequest) {

        log.debug("[Controller] creando el libro {} ", libroRequest.getTitulo());
        Libro libro = libroMapper.libroRequestToLibroDomain(libroRequest);

        try {
            Libro libroGuardado = libroService.guardarLibro(libro);
            log.info("[Controller] el libro {} se ha creado ", libro.getTitulo());
            LibroResponse libroResponse = libroMapper.libroDomainToLibroResponse(libroGuardado);
            return ResponseEntity.status(HttpStatus.CREATED).body(libroResponse);

        } catch (LibroException e) {
            log.error("[Controller] Error al intentar guardar el libro: ", e.getMessage(), e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarLibro(@PathVariable Long id,
                                                  @RequestBody LibroRequest libroRequest) {

        log.debug("[Controller] actualizando libro con id {} ", id);

        Libro libro = libroMapper.libroRequestToLibroDomain(libroRequest);

        try {
            LibroResponse libroActualizadoResponse = libroMapper.libroDomainToLibroResponse(
                    libroService.actualizarLibro(id, libro));

            log.info("[Controller] el libro con id {} se ha actualizado", id);

            return ResponseEntity.ok(libroActualizadoResponse);
        } catch (LibroException e) {
            log.error("[Controller] Error al actualizar libro con ID {}: {}", id, e.getMessage(),
                    e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> actualizarParcialmenteLibro(@PathVariable Long id,
                                                              @RequestBody Map<String, String> camposActualizados) {

        log.debug("[Controller] se va a actualizar algun campo del libro con id {}", id);

        try {

            Libro libroActualizado = libroService.actualizarParcialmenteLibro(id,
                    camposActualizados);
            LibroResponse libroResponse = libroMapper.libroDomainToLibroResponse(libroActualizado);

            log.info("[Controller] se han actualizado los siguientes campos del libro con ID {}:",
                    id);
            camposActualizados.forEach(
                    (campo, valor) -> log.info("Campo {} actualizado a {}", campo, valor));

            return ResponseEntity.ok(libroResponse);
        } catch (LibroException e) {
            log.error("[Controller] Error en actualización parcial del libro con ID {}: {}", id,
                    e.getMessage(), e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> borrarLibro(@PathVariable Long id) {

        log.debug("[Controller] eliminando libro con id {}", id);

        try {

            libroService.borrarLibroPorId(id);
            log.info("[Controller] el libro con id {} se ha eliminado", id);
            return ResponseEntity.noContent().build();

        } catch (LibroException e) {
            log.error("[Controller] Error al eliminar el libro con id {}: {}", id, e.getMessage(),e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

}
