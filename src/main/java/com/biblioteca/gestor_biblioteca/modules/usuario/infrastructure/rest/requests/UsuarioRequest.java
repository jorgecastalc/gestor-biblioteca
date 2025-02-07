package com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.requests;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;
}
