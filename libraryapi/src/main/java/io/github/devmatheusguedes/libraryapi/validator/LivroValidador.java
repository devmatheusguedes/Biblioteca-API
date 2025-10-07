package io.github.devmatheusguedes.libraryapi.validator;

import io.github.devmatheusguedes.libraryapi.exepcion.CampoObrigatorioException;
import io.github.devmatheusguedes.libraryapi.exepcion.RegistroDuplicadoExcepcion;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import io.github.devmatheusguedes.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LivroValidador {
    private final LivroRepository repository;

    private static final int  ANO_EXIGENCIA_PRECO = 2020;

    public LivroValidador(LivroRepository repository) {
        this.repository = repository;
    }


    public void validar(Livro livro){
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoExcepcion("ISBN já esta cadastrado");
        }
        if (isPrecoObrigatorioNulo(livro)){
            throw new CampoObrigatorioException("preço", "O campo preço, para livros a partir do ano de 2020, " +
                    "tem que ser preenchido");
        }
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null){
            return livroEncontrado.isPresent();
        }


        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }

    private boolean isPrecoObrigatorioNulo(Livro livro){
        return (livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO);
    }
}
