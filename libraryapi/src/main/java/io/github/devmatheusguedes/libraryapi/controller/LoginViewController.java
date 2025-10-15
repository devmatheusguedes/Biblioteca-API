package io.github.devmatheusguedes.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {
    @GetMapping("/login") // utilizamos esta classe para receber a requisição de uma pagina e
    // devolve a pagina em si
    public String paginaLogin(){
        return "login";
    }
}
