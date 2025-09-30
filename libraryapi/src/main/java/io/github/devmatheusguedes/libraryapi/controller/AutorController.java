package io.github.devmatheusguedes.libraryapi.controller;

import io.github.devmatheusguedes.libraryapi.controller.dto.AutorDTO;
import io.github.devmatheusguedes.libraryapi.controller.dto.ErroResposta;
import io.github.devmatheusguedes.libraryapi.controller.mappers.AutorMapper;
import io.github.devmatheusguedes.libraryapi.exepcion.RegistroDuplicadoExcepcion;
import io.github.devmatheusguedes.libraryapi.model.Autor;
import io.github.devmatheusguedes.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
// http://localhost:8080/autores
// a classe do tipo interface GenericController é usada para criar uma uri com o id da entidade
// para lançar no header.
public class AutorController implements GenericController {
    @Autowired
    private final AutorService autorService;
    @Autowired
    private final AutorMapper mapper;


    public AutorController(AutorService autorService, AutorMapper mapper) {
        this.autorService = autorService;
        this.mapper = mapper;

    }

    //@RequestMapping(method = RequestMethod.POST) outra forma de declarar uma requisição
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {

        Autor autor = mapper.toAutor(dto);
        Autor autorSalvo = autorService.salvar(autor);
        // http://localhost:8080/autores/{id} sendo retornado no header
        URI uri = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toAutorDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deletar(idAutor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> filtrar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> autores = autorService.filtarByExample(nome, nacionalidade);
        List<AutorDTO> listAutotDTO = autores
                .stream()
                .map(mapper::toAutorDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listAutotDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable String id, @RequestBody @Valid AutorDTO autorDTO) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var autor = autorOptional.get();
        autor = mapper.toAutor(autorDTO);

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();

    }
}
