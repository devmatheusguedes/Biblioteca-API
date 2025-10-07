package io.github.devmatheusguedes.libraryapi.exepcion;

import lombok.Getter;

public class CampoObrigatorioException extends RuntimeException{
    @Getter
    public String campo;

    public CampoObrigatorioException(String campo, String mensagem){
        super(mensagem);
        this.campo = campo;
    }

}
