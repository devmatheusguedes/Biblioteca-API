package io.github.devmatheusguedes.libraryapi.controller.mappers;

import io.github.devmatheusguedes.libraryapi.controller.dto.AutorDTO;
import io.github.devmatheusguedes.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.devmatheusguedes.libraryapi.controller.dto.ResultadoPesquisaLivro;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import io.github.devmatheusguedes.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
// o parametro uses que recebe o autormapper.class é usado para indicarmos que o nosse mapper
// também precisa de outro mapper para funcionar, ele vai, por baixo dos panos,
// chamar o autor para autorDTo
public abstract class LivroMapper {
    @Autowired
    AutorRepository autorRepository;
    @Autowired
    AutorMapper mapper;
    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toLivro(CadastroLivroDTO dto);
    public abstract CadastroLivroDTO toCadastroLivroDTO(Livro livro);

    // o mapping utilizado abaixo também pode ser usado para tranformar o autor rm um autor dto/
    // sem a necessidade de utilizar o parametro users
    //@Mapping(target = "autorDTO", expression = "java(mapper.toAutorDTO( autorRepository.findById(dto.getAutor().getId()).orElse(null) ))")
    public abstract ResultadoPesquisaLivro toResultadoPesquisaLivro(Livro dto);
}
