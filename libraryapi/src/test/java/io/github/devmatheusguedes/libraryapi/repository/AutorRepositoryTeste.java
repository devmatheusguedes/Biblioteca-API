package io.github.devmatheusguedes.libraryapi.repository;

import io.github.devmatheusguedes.libraryapi.model.Autor;
import io.github.devmatheusguedes.libraryapi.model.GeneroLivro;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTeste {
    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTeste(){
        Autor autor = new Autor();
        autor.setNome("dicrapio");
        autor.setNacionalidade("frança");
        autor.setDataNascimento(LocalDate.of(1976, 3, 2));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor salvo: "+autorSalvo);
    }

    @Test
    public void atualizarTest(){
        UUID id = UUID.fromString("38b18b0f-94f4-4587-aff8-90d356721bde");
        Optional<Autor> possivelAutor = autorRepository.findById(id);
        if (possivelAutor.isPresent()){
            Autor autor = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autor);

            autor.setNome("matheuscomaga");
            autorRepository.save(autor);
        }

    }

    @Test
    public void listarAutores(){
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void count(){
        System.out.println("quantidade de registros de autores: "+autorRepository.count());
    }

    @Test
    public void deletarPorID(){
        UUID id = UUID.fromString("26fe1f32-8980-4a8b-b84c-90af0f2e817c");
        autorRepository.deleteById(id);
    }
    @Test
    public void deletarPorObjeto(){
        UUID id = UUID.fromString("38b18b0f-94f4-4587-aff8-90d356721bde");
        var matheuscomaga = autorRepository.findById(id).get(); // usar o optional para pegar o objeto. pois, é mais seguro.
        autorRepository.delete(matheuscomaga);
    }

    @Test
    void salvarAutorComLivrosSemCascade(){

        Autor autor = new Autor();
        autor.setNome("César Duarte");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 3, 10));

        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setIsbn("65t43-986ur");
        livro.setPreco(BigDecimal.valueOf(500));
        livro.setTitulo("estrada sem forma");
        livro.setDataPublicacao(LocalDate.of(2025, 4, 6));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setIsbn("4532-o875");
        livro2.setPreco(BigDecimal.valueOf(330));
        livro2.setTitulo("abismo com trama");
        livro2.setDataPublicacao(LocalDate.of(2029, 6, 6));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);

        livroRepository.saveAll(autor.getLivros()); // usar caso o cascade na propriedade autor não estiver ativado

    }

    @Test
    void salvarAutorComLivroscomCascade(){

        Autor autor = new Autor();
        autor.setNome("Marcos");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1980, 3, 10));

        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setIsbn("456377-99668");
        livro.setPreco(BigDecimal.valueOf(500));
        livro.setTitulo("the last of end");
        livro.setDataPublicacao(LocalDate.of(2005, 4, 6));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setIsbn("45007-9112");
        livro2.setPreco(BigDecimal.valueOf(30));
        livro2.setTitulo("how are you?");
        livro2.setDataPublicacao(LocalDate.of(2009, 6, 6));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);

        // livroRepository.saveAll(autor.getLivros()); // usar caso o cascade na propriedade autor não estiver ativado

    }

    @Test
    @Transactional
    void buscarlivrosDoAutor(){
        UUID id = UUID.fromString("38188707-e5c7-4b7d-b555-dec91fef7128");
        Autor autor = autorRepository.findById(id).orElse(null);

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        assert autor != null;
        autor.setLivros(livrosLista);
        autor.getLivros().forEach(System.out::println);
    }
}
