package io.github.devmatheusguedes.libraryapi.exepcion;

public class OperacaoNaoPermitidaExeption extends RuntimeException{
    public OperacaoNaoPermitidaExeption(String mensagem){
        super(mensagem);
    }
}
