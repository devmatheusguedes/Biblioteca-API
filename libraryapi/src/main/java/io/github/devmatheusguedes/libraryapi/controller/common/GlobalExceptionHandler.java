package io.github.devmatheusguedes.libraryapi.controller.common;

import io.github.devmatheusguedes.libraryapi.controller.dto.ErroCampo;
import io.github.devmatheusguedes.libraryapi.controller.dto.ErroResposta;
import io.github.devmatheusguedes.libraryapi.exepcion.CampoObrigatorioException;
import io.github.devmatheusguedes.libraryapi.exepcion.OperacaoNaoPermitidaExeption;
import io.github.devmatheusguedes.libraryapi.exepcion.RegistroDuplicadoExcepcion;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handlerMethidArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaDeErro = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(
                        fe.getField(),
                        fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação."
                , listaDeErro);
    }

    @ExceptionHandler(RegistroDuplicadoExcepcion.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handlerRegistroDuplicadoException(RegistroDuplicadoExcepcion e){
        return ErroResposta.conflito(e.getMessage());
    }

    // ESSE PRIMEIRO @ É USADO PARA INDICAR A EXCESSÃO A SER TRATADA
    @ExceptionHandler(OperacaoNaoPermitidaExeption.class)
    // ESSE SEGUNGO @ É USADO PARA INDICAR O STATUS A SER LANÇADO
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handlerOperacaoNaoPermitida
            (OperacaoNaoPermitidaExeption e){
        return ErroResposta.RespostaPadrao("Operação não permitia" + e.getMessage());
    }
    @ExceptionHandler(CampoObrigatorioException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // UNPROCESSABLE_ENTITY é usado para erros de validação
    public ErroResposta handlerCampoObrigatorioException(CampoObrigatorioException e){
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                List.of(new ErroCampo(e.getCampo(),
                        e.getMessage()))
                );
    }
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErroResposta handleErrosNaoTratados(RuntimeException e){
//        return new ErroResposta(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "Ocorreu um erro inesperado. Cotate-nos para resolvermos o problema. " + e.getMessage()
//                , List.of());
//    }
}
