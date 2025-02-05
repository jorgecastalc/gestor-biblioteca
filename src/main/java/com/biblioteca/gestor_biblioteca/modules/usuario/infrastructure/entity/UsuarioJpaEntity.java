package com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "telefono",nullable = false)
    private String telefono;

    @Column(name = "fechaRegistro",nullable = false)
    private LocalDate fechaRegistro;
}
