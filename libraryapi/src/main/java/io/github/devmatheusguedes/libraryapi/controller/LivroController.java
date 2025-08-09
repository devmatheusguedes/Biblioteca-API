package io.github.devmatheusguedes.libraryapi.controller;

import io.github.devmatheusguedes.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.devmatheusguedes.libraryapi.controller.dto.ErroResposta;
import io.github.devmatheusguedes.libraryapi.exepcion.RegistroDuplicadoExcepcion;
import io.github.devmatheusguedes.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
public class LivroController {
    private final LivroService service;
    public  LivroController(LivroService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO livroDTO){
        try {
            return ResponseEntity.ok(livroDTO);
        }catch (RegistroDuplicadoExcepcion excepcion){
            ErroResposta conflito = ErroResposta.conflito(excepcion.getMessage());
            return ResponseEntity.status(conflito.status()).body(conflito);
        }
    }
}
