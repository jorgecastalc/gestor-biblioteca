package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.mapper;

import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.entity.LibroJpaEntity;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.requests.LibroRequest;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.responses.LibroResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibroMapper {

    Libro libroJpaEntityToLibroDomain(LibroJpaEntity libroJpaEntity);
    LibroJpaEntity libroDomainToLibroJpaEntity(Libro libro);

    List<LibroJpaEntity> libroListToLibroEntityList(List<Libro> libros);
    List<Libro> libroEntityListToLibroList(List<LibroJpaEntity> libros);

    Libro libroResponseToLibroDomain(LibroResponse libroResponse);
    LibroResponse libroDomainToLibroResponse(Libro libro);

    List<Libro> libroResponseListToLibroList(List<LibroResponse> libros);
    List<LibroResponse> libroListToLibroResponseList(List<Libro> libros);

    Libro libroRequestToLibroDomain(LibroRequest libroRequest);
    LibroRequest libroDomainToLibroRequest(Libro libro);

    List<Libro> libroRequestListToLibroList(List<LibroRequest> libros);
    List<LibroRequest> libroListToLibroRequestList(List<Libro> libros);
}
