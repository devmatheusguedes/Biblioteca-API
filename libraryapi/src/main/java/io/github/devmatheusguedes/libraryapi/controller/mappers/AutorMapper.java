package io.github.devmatheusguedes.libraryapi.controller.mappers;

import io.github.devmatheusguedes.libraryapi.controller.dto.AutorDTO;
import io.github.devmatheusguedes.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    Autor toAutor(AutorDTO autorDTO);
    AutorDTO toAutorDTO(Autor autor);
}
