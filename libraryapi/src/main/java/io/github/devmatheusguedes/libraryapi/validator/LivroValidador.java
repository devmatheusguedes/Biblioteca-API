package io.github.devmatheusguedes.libraryapi.validator;

import io.github.devmatheusguedes.libraryapi.exepcion.RegistroDuplicadoExcepcion;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import io.github.devmatheusguedes.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidador {
    private LivroRepository repository;

    public void validar(Livro livro){
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoExcepcion("ISBN já esta cadastrado");
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
}
