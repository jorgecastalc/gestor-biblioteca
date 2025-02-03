package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.responses;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibroResponse {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate fechaPublicacion;
}
