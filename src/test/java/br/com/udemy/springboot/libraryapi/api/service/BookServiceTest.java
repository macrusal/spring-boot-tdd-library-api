package br.com.udemy.springboot.libraryapi.api.service;

import br.com.udemy.springboot.libraryapi.api.model.repository.BookRepository;
import br.com.udemy.springboot.libraryapi.api.service.impl.BookServiceImpl;
import br.com.udemy.springboot.libraryapi.api.model.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setUp() {
        this.service = new BookServiceImpl( repository );
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest() {
        //cenario
        Book book = Book.builder()
                .isbn("01234560")
                .autor("Paulo Coelho")
                .title("O Livro do Paulo Coelho")
                .build();
        Mockito.when(repository.save(book)).thenReturn(
                Book.builder()
                    .id(1L)
                    .isbn("01234560")
                    .autor("Paulo Coelho")
                    .title("O Livro do Paulo Coelho")
                    .build());

        //execucao
        Book savedBook = service.save(book);

        //verificacao
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("01234560");
        assertThat(savedBook.getAutor()).isEqualTo("Paulo Coelho");
        assertThat(savedBook.getTitle()).isEqualTo("O Livro do Paulo Coelho");

    }

}
