package io.github.devmatheusguedes.libraryapi.controller;

import io.github.devmatheusguedes.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.devmatheusguedes.libraryapi.controller.dto.ErroResposta;
import io.github.devmatheusguedes.libraryapi.controller.dto.ResultadoPesquisaLivro;
import io.github.devmatheusguedes.libraryapi.controller.mappers.LivroMapper;
import io.github.devmatheusguedes.libraryapi.exepcion.RegistroDuplicadoExcepcion;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import io.github.devmatheusguedes.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("livros")
public class LivroController implements GenericController{
    private final LivroService service;
    private final LivroMapper mapper;


    public  LivroController(LivroService service, LivroMapper mapper){
        this.service = service;
        this.mapper = mapper;

    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO livroDTO){
        Livro livro = mapper.toLivro(livroDTO);
        service.salvar(livro);
        URI url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoPesquisaLivro> obterDetalhes(@PathVariable("id") String id){
        UUID id_livro = UUID.fromString(id);

        return service
                .obterPorId(id_livro)
                .map(livro -> {
                    ResultadoPesquisaLivro dto = mapper.toResultadoPesquisaLivro(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        UUID id_livro = UUID.fromString(id);
        Optional<Livro> livro = service.obterPorId(id_livro);
        if (livro.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        service.deletar(id_livro);
        return ResponseEntity.noContent().build();
    }
}
