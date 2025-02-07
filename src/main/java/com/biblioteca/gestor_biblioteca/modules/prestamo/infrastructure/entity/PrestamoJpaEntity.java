package com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.entity;

import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.entity.LibroJpaEntity;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.entity.UsuarioJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prestamos")
public class PrestamoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "libro_id", unique = true)
    private LibroJpaEntity libro;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioJpaEntity usuario;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;
}
