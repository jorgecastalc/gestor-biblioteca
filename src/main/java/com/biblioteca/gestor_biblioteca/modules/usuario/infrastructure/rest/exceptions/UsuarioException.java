package com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.exceptions;

import lombok.Getter;

@Getter
public class UsuarioException extends RuntimeException {
    private final int errorCode;

    public UsuarioException(int errorCode , String descripcion) {

        super(descripcion);
        this.errorCode = errorCode;
    }
}
