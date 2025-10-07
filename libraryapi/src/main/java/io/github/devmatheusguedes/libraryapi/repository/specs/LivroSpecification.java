package io.github.devmatheusguedes.libraryapi.repository.specs;

import io.github.devmatheusguedes.libraryapi.model.GeneroLivro;
import io.github.devmatheusguedes.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/***
 * essa classe é utilizada para fazer pesquisas dinamicas.
 * sem a necessidade de escrever cóigo sql
 */
public class LivroSpecification {
    public static Specification<Livro> isbnEquals(String isbn){

        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }
    // colocar o nome do campo mais a propriedade que se deseja utilizar para filtar a pesquisa
    // como o 'tituloLike', que é equivalente a um join
    public static Specification<Livro> tituloLike(String titulo){
        // a propriedade % é usada para comparar a string de pesquisa fornecida pelo o usuario
        // em qualquer lugar da string que estiver no campo titulo
        // como se o autor estivesse procurando por uma parte do titulo
        return (root, query, cb)-> cb.like(cb.upper(root.get("titulo")), "%"+titulo.toUpperCase()+"%");
    }

    public static Specification<Livro> generoEquals(GeneroLivro genero){
        return ((root, query, cb) -> cb.equal(root.get("genero"), genero));
    }

    public static Specification<Livro> dataPublicacaoEquals(Integer dataPublicacao){
        // select to_char(dataPublicacao, 'yyyy') = :anoPublicacao
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY"))
                        ,dataPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome){
        // um join mais definido, podendo ser alterado para rigth_join, left_join e inner_join
        return (root, query, cb) -> {
            Join<Object, Object> autor = root.join("autor", JoinType.LEFT);
            return cb.like(cb.upper(autor.get("nome")), "%"+nome.toUpperCase()+"%");
        };
        // forma mais simples:
        //return (root, query, cb) -> {
            //return cb.like(cb.upper(root.get("autor").get("nome")), "%"+nome+"%");
       // };
    }


}
