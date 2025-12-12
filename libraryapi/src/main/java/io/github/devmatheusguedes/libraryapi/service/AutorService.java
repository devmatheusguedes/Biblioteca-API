package io.github.devmatheusguedes.libraryapi.service;

import io.github.devmatheusguedes.libraryapi.model.Autor;
import io.github.devmatheusguedes.libraryapi.model.Usuario;
import io.github.devmatheusguedes.libraryapi.repository.AutorRepository;
import io.github.devmatheusguedes.libraryapi.security.SecurityService;
import io.github.devmatheusguedes.libraryapi.validator.AutorValidador;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidador validador;
    private final SecurityService securityService;

    public AutorService(AutorRepository autorRepository, AutorValidador validador, SecurityService securityService) {
        this.autorRepository = autorRepository;
        this.validador = validador;
        this.securityService = securityService;
    }

    public Autor salvar(Autor autor){
        validador.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletar(UUID id){
        autorRepository.deleteById(id);
    }

    public void atualizar(Autor autor){
        if (autor.getId() == null){
            throw new IllegalArgumentException("O autor precisa estar cadastrado para atualizar.");
        }
        validador.validar(autor);
        autorRepository.save(autor);
    }

    public List<Autor> filtar(String nome, String nacionalidade){
        if (nome!=null&&nacionalidade!=null){
            return autorRepository.findBynomeAndNacionalidade(nome, nacionalidade);
        }
        if (nome!=null){
            return autorRepository.findByNome(nome);
        }
        if (nacionalidade!=null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> filtarByExample(String nome, String nacionalidade){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues() // ignorar campos nulos
                .withIgnoreCase() // padronizar caracteres caixa alta ou caixa baixa são iguai
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // pesquisar por qualquer parte da String no BD
        Example<Autor> autorExample = Example.of(autor, matcher);

        return autorRepository.findAll(autorExample);
    }
}
