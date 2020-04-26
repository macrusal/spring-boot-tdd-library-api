package br.com.udemy.springboot.libraryapi.api.service;

import br.com.udemy.springboot.libraryapi.model.entity.Book;
import br.com.udemy.springboot.libraryapi.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author macrusal on 26/04/20
 * @project library-api
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;

    @Test
    @DisplayName("Deve salvat um livro")
    public void saveBookTest() {
        //cenario
        Book book = Book.builder()
                .isbn("01234560")
                .autor("Paulo Coelho")
                .title("O Livro do Paulo Coelho")
                .build();

        //execucao
        Book savedBook = service.save(book);

        //verificacao
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("01234560");
        assertThat(savedBook.getAutor()).isEqualTo("Paulo Coelho");
        assertThat(savedBook.getTitle()).isEqualTo("O Livro do Paulo Coelho");

    }
}
