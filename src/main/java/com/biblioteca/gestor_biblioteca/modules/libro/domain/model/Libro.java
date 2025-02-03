package com.biblioteca.gestor_biblioteca.modules.libro.domain.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Libro {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate fechaPublicacion;

}
