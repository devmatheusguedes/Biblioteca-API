package io.github.devmatheusguedes.libraryapi.repository;

import io.github.devmatheusguedes.libraryapi.model.Autor;
import io.github.devmatheusguedes.libraryapi.model.GeneroLivro;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see LivroRepository
 */
@SpringBootTest
public class LivroRepositoryTest {
    @Autowired
    LivroRepository livroRepository;
    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTeste(){
        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setIsbn("3456-098");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setTitulo("Rio");
        livro.setDataPublicacao(LocalDate.of(2010, 4, 6));

        Autor autor = autorRepository
                .findById(UUID
                        .fromString("a49a2e95-c02a-490f-bcea-796c3803bf27"))
                .orElse(null);
        livro.setAutor(autor);

        livroRepository.save(livro);
    }
    @Test
    void salvarCascadeTeste(){
        Autor autor = new Autor();
        autor.setNome("César Duarte");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 3, 10));

        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setIsbn("34w56-0558");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setTitulo("House star");
        livro.setDataPublicacao(LocalDate.of(2020, 4, 6));
        livro.setAutor(autor);

        livroRepository.save(livro);
    }
    @Test
    void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("c279bfd5-94ce-47b1-97bd-f09dea578788");
        var livroParaAtualizar = livroRepository.findById(id).orElse(null);

        UUID id_autor = UUID.fromString("b1bad984-9ad1-4654-968c-40dd0c03fc00");
        Autor autor = autorRepository.findById(id_autor).orElse(null);
        livroParaAtualizar.setAutor(autor);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("5f528e45-f6a5-4292-b765-2a01f153c48b");
        livroRepository.deleteById(id);
    }

    @Test
    void deletarCascade(){// ao ativar a funçõo cascade no livro, você apaga tanto o livro quanto a autor
        UUID id = UUID.fromString("5f528e45-f6a5-4292-b765-2a01f153c48b");
        livroRepository.deleteById(id);
    }
    @Test
    @Transactional
    void buscarLivro(){
        UUID idLivro = UUID.fromString("dd0e0e77-ac85-40d6-b54a-1218a4b80fad");
        Livro livro = livroRepository.findById(idLivro).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void buscarLivroPoTitulo(){
        String titulo = "Rio";

        List<Livro> livros = livroRepository.findByTitulo(titulo);
        livros.forEach(System.out::println);
    }
    @Test
    void buscarLivroPoIsbn(){
        String isbn = "3456-098";

        List<Livro> livros = livroRepository.findByIsbn(isbn);
        livros.forEach(System.out::println);
    }

    @Test
    void buscarPorGeneroAndPreco(){
        List<Livro> listaGeneroAndPreco = livroRepository.findByGeneroAndPreco(GeneroLivro.CIENCIA, new BigDecimal(500));
        listaGeneroAndPreco.forEach(System.out::println);

    }

    @Test
    void buscarLivroComQueryJPQL(){
        List<Livro> livros = livroRepository.listarTodosOrdenadoPorTituloAndPreco();
        livros.forEach(System.out::println);
    }

    @Test
    void buscarAutorDoLivroComQueryJPQL(){
        List<Autor> autoresDosLivros = livroRepository.listarAutoresDosLivros();
        autoresDosLivros.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivros(){
        List<String> titulosDosLivros = livroRepository.listarLivrosPorTitulosDistintos();
        titulosDosLivros.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeAutoresBrasilerios(){
        List<String> autors = livroRepository.listarGenerosAutoresBrsilerios();
        autors.forEach(System.out::println);
    }

    @Test
    void listarPeloGeneroQueryParam(){
        var listaDeLivros = livroRepository.findByGrnero(GeneroLivro.CIENCIA, "dataPublicacao");
        listaDeLivros.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloAndprecoPosisionalParam(){
        var livros = livroRepository.findByTituloAndPrecoPosicionalParam("estrada sem forma",
                new BigDecimal(500));
        livros.forEach(System.out::println);
    }
    @Test
    void deletarPorGnero(){
        livroRepository.deleteByGenero(GeneroLivro.FANTASIA);
    }

    @Test
    void updateDataPublicacao(){
        livroRepository.updateByDataPublicacao(LocalDate.of(2000, 4, 3));
    }


}