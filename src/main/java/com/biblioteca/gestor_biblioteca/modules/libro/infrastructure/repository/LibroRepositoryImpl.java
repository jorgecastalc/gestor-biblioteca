package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.repository;


import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.repository.LibroRepository;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.entity.LibroJpaEntity;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.mapper.LibroMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class LibroRepositoryImpl implements LibroRepository {

    private final LibroJpaRepository libroJpaRepository;
    private final LibroMapper libroMapper;

    @Override
    public List<Libro> obtenerLibros() {

        return libroJpaRepository.findAll().stream().map(
                libroMapper::libroJpaEntityToLibroDomain).toList();

    }

    @Override
    public Optional<Libro> obtenerLibroPorId(Long id) {
        return libroJpaRepository.findById(id).map(libroMapper::libroJpaEntityToLibroDomain);
    }

    @Override
    public Libro guardarActualizarLibro(Libro libro) {

        LibroJpaEntity libroJpaEntity = libroMapper.libroDomainToLibroJpaEntity(libro);

        return libroMapper.libroJpaEntityToLibroDomain(libroJpaRepository.save(libroJpaEntity));
    }

    @Override
    public void borrarLibroPorId(Long id) {
        libroJpaRepository.deleteById(id);

    }
}
