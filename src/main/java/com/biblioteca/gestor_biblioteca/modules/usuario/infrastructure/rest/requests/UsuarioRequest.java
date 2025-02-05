package com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.requests;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;
}
