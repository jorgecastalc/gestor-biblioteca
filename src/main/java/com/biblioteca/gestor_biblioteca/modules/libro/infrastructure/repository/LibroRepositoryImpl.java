package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.repository;


import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.repository.LibroRepository;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.mapper.LibroMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class LibroRepositoryImpl implements LibroRepository {

    private final LibroJpaRepository libroJpaRepository;
    private final LibroMapper libroMapper;

    @Override
    public List<Libro> obtenerLibros() {

        return libroJpaRepository.findAll().stream().map(
                libroMapper::toDomain).toList();

    }

    @Override
    public Optional<Libro> obtenerLibroPorId(Long id) {
        return Optional.empty();
    }

    @Override
    public Libro guardarLibro(Libro libro) {
        return null;
    }

    @Override
    public void borrarLibroPorId(Long id) {

    }
}
