package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.entity;


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
@Table(name = "libros")
public class LibroJpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "autor",nullable = false)
    private String autor;

    @Column(name = "isbn",nullable = false, unique = true)
    private String isbn;

    @Column(name = "fecha_publicacion",nullable = false)
    private LocalDate fechaPublicacion;
}
