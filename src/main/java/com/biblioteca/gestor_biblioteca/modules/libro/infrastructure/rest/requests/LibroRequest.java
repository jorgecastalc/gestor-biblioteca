package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.requests;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibroRequest {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate fechaPublicacion;
}
