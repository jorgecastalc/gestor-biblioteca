package com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.exceptions;

import lombok.Getter;

@Getter
public class LibroException extends RuntimeException {

    private final int errorCode;

    public LibroException(int errorCode , String descripcion) {

        super(descripcion);
        this.errorCode = errorCode;
    }
}
