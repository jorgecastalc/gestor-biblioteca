package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.mapper;

import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.entity.LibroJpaEntity;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.responses.LibroResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibroMapper {

    Libro toDomain(LibroJpaEntity libroJpaEntity);
    LibroJpaEntity toEntity(Libro libro);

    List<LibroJpaEntity> libroListToLibroEntityList(List<Libro> libros);
    List<Libro> libroEntityListToLibroList(List<LibroJpaEntity> libros);

    Libro toResponse(LibroResponse libroResponse);
    LibroResponse toDomain(Libro libro);

    List<Libro> libroResponseListToLibroList(List<LibroResponse> libros);
    List<LibroResponse> libroListToLibroResponseList(List<Libro> libros);

}
