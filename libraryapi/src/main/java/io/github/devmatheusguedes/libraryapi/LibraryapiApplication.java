package io.github.devmatheusguedes.libraryapi;

import io.github.devmatheusguedes.libraryapi.model.Autor;
import io.github.devmatheusguedes.libraryapi.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryapiApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(LibraryapiApplication.class, args);
		AutorRepository repository = context.getBean(AutorRepository.class);
		exemploSalvarResgistro(repository);
	}
	public static void exemploSalvarResgistro(AutorRepository autorRepository){
		String nome = "matheus";
		Autor autor = new Autor();
		autor.setNome(nome);
		autor.setNacionalidade("Brasileiro");
		autor.setDataNascimento(LocalDate.of(2004, 2, 10));
		Autor autorSalvo = autorRepository.save(autor);
		System.out.println("Autor salvo: "+ autorSalvo);
	}
}
