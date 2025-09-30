package io.github.devmatheusguedes.libraryapi.service;

import io.github.devmatheusguedes.libraryapi.model.Livro;
import io.github.devmatheusguedes.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class LivroService {
    private final LivroRepository repository;
    public LivroService(LivroRepository repository){
        this.repository = repository;
    }

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(UUID id){
        repository.deleteById(id);
    }

}
