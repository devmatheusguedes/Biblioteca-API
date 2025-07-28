package io.github.devmatheusguedes.libraryapi.service;

import io.github.devmatheusguedes.libraryapi.model.Autor;
import io.github.devmatheusguedes.libraryapi.model.GeneroLivro;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import io.github.devmatheusguedes.libraryapi.repository.AutorRepository;
import io.github.devmatheusguedes.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransacaoService {
    @Autowired
    private LivroRepository repository;
    private AutorRepository autorRepository;

    /*
    as operações de escrita só são enviadas para o banco de dados no final da transação,
    Logo, se houver algum erro antes de finalizar as transações, mesmo que alguma transações já tenham sido
    processadas, nehuma das operações vaô ser enviadas ao banco de dados.
     */
    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("juliana");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 3, 10));

        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setIsbn("34w56-0558");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setTitulo("livro da juliana");
        livro.setDataPublicacao(LocalDate.of(2020, 4, 6));
        livro.setAutor(autor);

        if (autor.getNome().equals("juliana") ){
            throw new RuntimeException("RollBack");
        }

        repository.save(livro);
    }

    @Transactional
    public void atualizarSemAtualizar(){
        UUID id = UUID.fromString("bca56974-5336-4178-9488-175339288aa1");
        Livro livro = repository.findById(id).orElse(null);
        if (livro != null) {
            livro.setDataPublicacao(LocalDate.of(1998, 7, 28));
        }
    }
}
