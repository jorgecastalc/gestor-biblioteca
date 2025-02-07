package com.biblioteca.gestor_biblioteca.modules.prestamo.domain.repository;

import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.model.Prestamo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepository {

    List<Prestamo> obtenerPrestamos();
    Optional<Prestamo> obtenerPrestamoPorId(Long id);
    Prestamo guardarActualizarPrestamo(Prestamo prestamo);
    void borrarPrestamoPorId(Long id);
    List<Prestamo> obtenerPrestamosPorLibroId(Long libroId);
    List<Prestamo> obtenerPrestamosPorUsuarioId(Long usuarioId);
}
