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

    Libro libroResponseToLibroDomain(LibroResponse libroResponse);
    LibroResponse libroDomainToLibroResponse(Libro libro);

    List<Libro> libroResponseListToLibroList(List<LibroResponse> libros);
    List<LibroResponse> libroListToLibroResponseList(List<Libro> libros);

    Libro libroRequestToLibroDomain(LibroRequest libroRequest);
    LibroRequest libroDomainToLibroRequest(Libro libro);

}
