package io.github.devmatheusguedes.libraryapi.controller.dto;

import io.github.devmatheusguedes.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/******
 * os campos abaixo precisam ter os nomes iguais aos campos do model para usar no mapper
 * @param id
 * @param isbn
 * @param titulo
 * @param dataPublicacao
 * @param genero
 * @param preco
 * @param autorDTO
 */
public record ResultadoPesquisaLivro(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDTO autor,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao,
        UUID usuario) {
}
