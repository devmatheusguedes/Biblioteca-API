package io.github.devmatheusguedes.libraryapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransacaoServiceTest {

    @Autowired
    TransacaoService service;

    @Test
    void Test(){
        service.executar();
    }

    @Test
    void atualizarSemAtualizar(){
        service.atualizarSemAtualizar();
    }
}