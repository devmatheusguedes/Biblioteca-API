package io.github.devmatheusguedes.libraryapi.service;

import io.github.devmatheusguedes.libraryapi.model.Autor;
import io.github.devmatheusguedes.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

    public Autor salvar(Autor autor){
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletar(UUID id){
        autorRepository.deleteById(id);
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
}
