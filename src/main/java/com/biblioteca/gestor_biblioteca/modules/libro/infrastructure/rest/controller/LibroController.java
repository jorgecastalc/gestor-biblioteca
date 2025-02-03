package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.controller;


import com.biblioteca.gestor_biblioteca.modules.libro.application.services.LibroService;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.mapper.LibroMapper;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.responses.LibroResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/libros")
@AllArgsConstructor
public class LibroController {

    private final LibroService libroService;
    private final LibroMapper libroMapper;


    @GetMapping
    public ResponseEntity<List<LibroResponse>> obtenerLibros(){

       List<Libro> librosList = libroService.obtenerLibros();

       List<LibroResponse> libroResponseList = libroMapper.libroListToLibroResponseList(librosList);

       return ResponseEntity.ok(libroResponseList);
    }

}
