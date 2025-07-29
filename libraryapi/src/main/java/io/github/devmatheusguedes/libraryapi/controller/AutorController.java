package io.github.devmatheusguedes.libraryapi.controller;

import io.github.devmatheusguedes.libraryapi.controller.dto.AutorDTO;
import io.github.devmatheusguedes.libraryapi.model.Autor;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import io.github.devmatheusguedes.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("autores")
// http://localhost:8080/autores
public class AutorController {
    @Autowired
    private AutorService autorService;

    public AutorController(AutorService autorService){
        this.autorService = autorService;
    }

    //@RequestMapping(method = RequestMethod.POST) outra forma de declarar uma requisição
    @PostMapping
    public ResponseEntity<UUID> salvar(@RequestBody AutorDTO autor){
        Autor autorEntidade = autor.mapearParaAutor();
        Autor autorSalvo = autorService.salvar(autorEntidade);
        // http://localhost:8080/autores/{id} sendo retornado no header
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorSalvo.getId())
                .toUri();
        return  ResponseEntity.created(uri).body(autorEntidade.getId());
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO autorDTO = new AutorDTO(autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());
            return ResponseEntity.ok(autorDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        autorService.deletar(idAutor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> filtrar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> autores = autorService.filtar(nome, nacionalidade);
        List<AutorDTO> listAutotDTO = autores
                .stream()
                .map(autor -> new AutorDTO(autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listAutotDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable String id, @RequestBody AutorDTO autorDTO){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var autor = autorOptional.get();
        autor.setNome(autorDTO.nome());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autor.setDataNascimento(autorDTO.dataNascimento());

        autorService.atualizar(autor);
        return ResponseEntity.noContent().build();
    }
}
