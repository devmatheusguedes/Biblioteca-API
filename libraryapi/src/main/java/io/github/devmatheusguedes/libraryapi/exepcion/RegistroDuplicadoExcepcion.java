package io.github.devmatheusguedes.libraryapi.exepcion;

public class RegistroDuplicadoExcepcion extends RuntimeException{
    public RegistroDuplicadoExcepcion(String message) {
        super(message);
    }
}
