package io.github.devmatheusguedes.libraryapi.service;

import io.github.devmatheusguedes.libraryapi.model.GeneroLivro;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import io.github.devmatheusguedes.libraryapi.repository.LivroRepository;
import io.github.devmatheusguedes.libraryapi.validator.LivroValidador;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import static io.github.devmatheusguedes.libraryapi.repository.specs.LivroSpecification.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class LivroService {
    private final LivroRepository repository;
    private final LivroValidador livroValidador;
    public LivroService(LivroRepository repository, LivroValidador livroValidador){
        this.repository = repository;
        this.livroValidador = livroValidador;
    }

    public Livro salvar(Livro livro) {
        livroValidador.validar(livro);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(UUID id){
        repository.deleteById(id);
    }

    // isbn, titulo, nome autor, genero, ano de publicacao
    public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor,
                                GeneroLivro genero, Integer dataPublicacao){

        // select * from livro where true = true
        Specification<Livro> specs = Specification.where(((root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction()));
        if (isbn != null){
            // query = query and isbn = :isbn
            specs = specs.and(isbnEquals(isbn));
        }
        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if (genero != null){
            specs = specs.and(generoEquals(genero));
        }

        if (dataPublicacao != null){
            specs = specs.and(dataPublicacaoEquals(dataPublicacao));
        }
        if (nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }
        return repository.findAll(specs);
    }

    public void atualizar(Livro livro) {
        livroValidador.validar(livro);
        repository.save(livro);
    }
}
