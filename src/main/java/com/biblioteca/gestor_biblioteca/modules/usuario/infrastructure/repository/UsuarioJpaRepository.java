package com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.repository;

import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.entity.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, Long> {
}
