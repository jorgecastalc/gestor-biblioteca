package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.repository;

import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.entity.LibroJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroJpaRepository extends JpaRepository<LibroJpaEntity, Long> {

}
