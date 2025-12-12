package io.github.devmatheusguedes.libraryapi.security;

import io.github.devmatheusguedes.libraryapi.model.Usuario;
import io.github.devmatheusguedes.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // pegando um objeto do
        if (authentication instanceof CustomAuthentication customAuthentication){
            return (Usuario) customAuthentication.getPrincipal();
        }

        return null;
    }
}
