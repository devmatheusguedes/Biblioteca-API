package io.github.devmatheusguedes.libraryapi.repository;

import io.github.devmatheusguedes.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
}
