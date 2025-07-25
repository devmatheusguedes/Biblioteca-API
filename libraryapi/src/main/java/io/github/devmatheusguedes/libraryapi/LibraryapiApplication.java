package io.github.devmatheusguedes.libraryapi;

import io.github.devmatheusguedes.libraryapi.model.Autor;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import io.github.devmatheusguedes.libraryapi.repository.AutorRepository;
import io.github.devmatheusguedes.libraryapi.repository.LivroRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing // usada para o spring ouvir as requisiçoes e atualizar automaticamente os campos
// no qual foram pré-programados para preencherem de forma dinamica.
public class LibraryapiApplication {

	public static void main(String[] args) {

		SpringApplication.run(LibraryapiApplication.class, args);

	}



}
