package io.github.devmatheusguedes.libraryapi.controller.mappers;

import io.github.devmatheusguedes.libraryapi.controller.dto.UsuarioDTO;
import io.github.devmatheusguedes.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

     Usuario toEntity(UsuarioDTO dto);
}
