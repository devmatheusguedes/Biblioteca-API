package io.github.devmatheusguedes.libraryapi.controller;

import io.github.devmatheusguedes.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.devmatheusguedes.libraryapi.controller.dto.ErroResposta;
import io.github.devmatheusguedes.libraryapi.controller.dto.ResultadoPesquisaLivro;
import io.github.devmatheusguedes.libraryapi.controller.mappers.LivroMapper;
import io.github.devmatheusguedes.libraryapi.exepcion.RegistroDuplicadoExcepcion;
import io.github.devmatheusguedes.libraryapi.model.GeneroLivro;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import io.github.devmatheusguedes.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping()
    public ResponseEntity<List<ResultadoPesquisaLivro>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "data-publicacao", required = false)
            Integer dataPublicacao
    ){
        var resultado = service.pesquisa(isbn, titulo, nomeAutor, genero, dataPublicacao);
        var lista = resultado
                .stream()
                .map(mapper::toResultadoPesquisaLivro)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id,
                                          @RequestBody @Valid
                                          CadastroLivroDTO livroDTO){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidade = mapper.toLivro(livroDTO);
                    livro.setDataPublicacao(entidade.getDataPublicacao());
                    livro.setTitulo(entidade.getTitulo());
                    livro.setAutor(entidade.getAutor());
                    livro.setIsbn(entidade.getIsbn());
                    livro.setGenero(entidade.getGenero());
                    livro.setPreco(entidade.getPreco());

                    service.atualizar(livro);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
