package io.github.devmatheusguedes.libraryapi.repository;

import io.github.devmatheusguedes.libraryapi.model.Autor;
import io.github.devmatheusguedes.libraryapi.model.GeneroLivro;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    // Query method
    // pesquisar os livros de determinado autor
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);
    List<Livro> findByIsbn(String isbn);

    // as concatenações podem ser usadas com qualquer expressão lógica: AND, OR, XOR,
    List<Livro> findByGeneroAndPreco(GeneroLivro generoLivro, BigDecimal preco);

    // JPQL -> referência ás entidades e as propriedades
    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    /**
     *
     * select a.*
     * from livro l
     * join autor as a on a.id = l.id_autor
     */
    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    /**
     * select distinct l.* from livro l
     */
    @Query("select distinct l.titulo from Livro l")
    List<String> listarLivrosPorTitulosDistintos();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileiro'
            order by l.genero
            """)
    List<String> listarGenerosAutoresBrsilerios();
}
