package io.github.devmatheusguedes.libraryapi.controller.dto;

import io.github.devmatheusguedes.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivro(
                                    UUID idLivro,
                                    String isbn,
                                     String titulo,
                                     LocalDate dataPublicacao,
                                     GeneroLivro generoLivro,
                                     BigDecimal preco,
                                     AutorDTO autorDTO) {
}
