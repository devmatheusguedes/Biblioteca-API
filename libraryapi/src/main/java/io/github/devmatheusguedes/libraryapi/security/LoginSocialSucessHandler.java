package io.github.devmatheusguedes.libraryapi.security;

import io.github.devmatheusguedes.libraryapi.model.Usuario;
import io.github.devmatheusguedes.libraryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UsuarioService service;
    private static String SENHA_PADRAO = "123456";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User principal = authenticationToken.getPrincipal();

        String email = principal.getAttribute("email");

        Usuario usuarioAutenticado = service.obterPorEmail(email);

        if (usuarioAutenticado == null){
            usuarioAutenticado = cadastrarUsuarioNaBase(email);

        }

        authentication  = new CustomAuthentication(usuarioAutenticado);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.onAuthenticationSuccess(request, response, authentication);
        System.out.println(email);
    }

    public Usuario cadastrarUsuarioNaBase(String email){
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setLogin(obterLogin(email));
        usuario.setSenha(SENHA_PADRAO);
        usuario.setRoles(List.of("OPERADOR"));
        service.salvar(usuario);
        return usuario;
    }

    private String obterLogin(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
