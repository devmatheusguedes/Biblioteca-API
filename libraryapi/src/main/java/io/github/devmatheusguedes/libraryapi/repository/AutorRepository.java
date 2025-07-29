package io.github.devmatheusguedes.libraryapi.repository;

import io.github.devmatheusguedes.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {
    public List<Autor> findByNome(String nome);
    public List<Autor> findByNacionalidade(String nacionalidade);
    public List<Autor> findBynomeAndNacionalidade(String nome, String nacionalidade1);
    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate dataNascimento, String nacionalidade);
}
