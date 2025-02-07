package com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.repository;

import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.model.Prestamo;
import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.repository.PrestamoRepository;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.entity.PrestamoJpaEntity;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.mapper.PrestamoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PrestamoRepositoryImpl implements PrestamoRepository {

    private final PrestamoJpaRepository prestamoJpaRepository;
    private final PrestamoMapper prestamoMapper;

    @Override
    public List<Prestamo> obtenerPrestamos() {

        return prestamoJpaRepository.findAll().stream()
                .map(prestamoMapper::prestamoJpaEntityToPrestamoDomain).toList();
    }

    @Override
    public Optional<Prestamo> obtenerPrestamoPorId(Long id) {
        return prestamoJpaRepository.findById(id)
                .map(prestamoMapper::prestamoJpaEntityToPrestamoDomain);
    }

    @Override
    public Prestamo guardarActualizarPrestamo(Prestamo prestamo) {

        PrestamoJpaEntity prestamoJpaEntity = prestamoMapper.prestamoDomainToPrestamoJpaEntity(
                prestamo);
        return prestamoMapper.prestamoJpaEntityToPrestamoDomain(
                prestamoJpaRepository.save(prestamoJpaEntity));
    }

    @Override
    public void borrarPrestamoPorId(Long id) {
        prestamoJpaRepository.deleteById(id);
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorLibroId(Long libroId) {
        return prestamoJpaRepository.findByLibroId(libroId).stream()
                .map(prestamoMapper::prestamoJpaEntityToPrestamoDomain)
                .toList();
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorUsuarioId(Long usuarioId) {
        return prestamoJpaRepository.findByUsuarioId(usuarioId).stream()
                .map(prestamoMapper::prestamoJpaEntityToPrestamoDomain)
                .toList();
    }
}
