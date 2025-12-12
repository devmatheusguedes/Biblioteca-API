package io.github.devmatheusguedes.libraryapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {
    @GetMapping("/login") // utilizamos esta classe para receber a requisição de uma pagina e
    // devolve a pagina em si
    public String paginaLogin(){
        return "login";
    }

    // controller da pagina padrão de login do spring
    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication){
        return "Olá, " + authentication.getName();
    }
}
