package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.controller;


import com.biblioteca.gestor_biblioteca.modules.libro.application.services.LibroService;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.mapper.LibroMapper;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.requests.LibroRequest;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.responses.LibroResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libros")
@AllArgsConstructor
public class LibroController {

    private final LibroService libroService;
    private final LibroMapper libroMapper;

    @GetMapping
    public ResponseEntity<List<LibroResponse>> obtenerLibros() {

        List<Libro> librosList = libroService.obtenerLibros();

        List<LibroResponse> libroResponseList = libroMapper.libroListToLibroResponseList(
                librosList);

        return ResponseEntity.ok(libroResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> obtenerLibroPorId(@PathVariable Long id) {

        Optional<Libro> libro = libroService.obtenerLibroPorId(id);

        return libro.map(
                libroResponse -> ResponseEntity.ok(
                        libroMapper.libroDomainToLibroResponse(libroResponse))).orElse(
                ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<LibroResponse> crearLibro(@RequestBody LibroRequest libroRequest) {

        Libro libro = libroMapper.libroRequestToLibroDomain(libroRequest);

        Libro libroGuardado = libroService.guardarLibro(libro);

        LibroResponse libroResponse = libroMapper.libroDomainToLibroResponse(libroGuardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(libroResponse);
    }

}
