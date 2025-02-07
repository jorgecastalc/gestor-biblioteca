package com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.rest.exceptions;

import lombok.Getter;

@Getter
public class PrestamoException extends RuntimeException {

    private final int errorCode;

    public PrestamoException(int errorCode , String descripcion) {

        super(descripcion);
        this.errorCode = errorCode;
    }

}
