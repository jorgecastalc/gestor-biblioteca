package com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.repository;

import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.entity.PrestamoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoJpaRepository extends JpaRepository<PrestamoJpaEntity,Long> {
    List<PrestamoJpaEntity> findByLibroId(Long libroId);
    List<PrestamoJpaEntity> findByUsuarioId(Long usuarioId);
}
