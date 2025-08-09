package io.github.devmatheusguedes.libraryapi.service;

import io.github.devmatheusguedes.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public class LivroService {
    private final LivroRepository repository;
    public LivroService(LivroRepository repository){
        this.repository = repository;
    }
}
