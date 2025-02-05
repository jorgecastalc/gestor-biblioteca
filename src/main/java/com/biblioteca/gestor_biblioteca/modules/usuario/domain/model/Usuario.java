package com.biblioteca.gestor_biblioteca.modules.usuario.domain.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;
}
