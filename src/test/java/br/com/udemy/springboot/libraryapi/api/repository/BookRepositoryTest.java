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

import java.util.Optional;

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
        Book book = createNewBook(isbn);
        entityManager.persist(book);

        //execucao
        boolean existsByIsbn = repository.existsByIsbn(isbn);

        //verificacao
        Assertions.assertThat(existsByIsbn).isTrue();
    }

    private Book createNewBook(String isbn) {
        return Book.builder()
                .autor("Autor desconhecido")
                .title("O Livro dos Segredos")
                .isbn(isbn)
                .build();
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

    @Test
    @DisplayName("Deve obter um livro por id")
    public void findByIdTest() {

        //cenario
        String isbn = "01234560";
        Book book = createNewBook(isbn);
        entityManager.persist(book);

        //execucao
        Optional<Book> foundBook = repository.findById(book.getId());

        //verificacao
        Assertions.assertThat(foundBook.isPresent()).isTrue();

    }
}
