package br.com.udemy.springboot.libraryapi.api.repository;

import br.com.udemy.springboot.libraryapi.api.model.entity.Book;
import br.com.udemy.springboot.libraryapi.api.model.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author macrusal on 26/04/20
 * @project library-api
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com o isbn informado")
    public void returnTrueWhenIsbnExists() {

        //cenario
        String isbn = "01234560";
        Book book = Book.builder()
                .autor("Autor desconhecido")
                .title("O Livro dos Segredos")
                .isbn(isbn)
                .build();
        entityManager.persist(book);

        //execucao
        boolean existsByIsbn = repository.existsByIsbn(isbn);

        //verificacao
        Assertions.assertThat(existsByIsbn).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um livro na base com o isbn informado")
    public void returnFalseWhenIsbnDoesntExists() {

        //cenario
        String isbn = "01234560";

        //execucao
        boolean existsByIsbn = repository.existsByIsbn(isbn);

        //verificacao
        Assertions.assertThat(existsByIsbn).isFalse();
    }
}
