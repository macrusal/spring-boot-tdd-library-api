package br.com.udemy.springboot.libraryapi.api.service;

import br.com.udemy.springboot.libraryapi.api.model.repository.BookRepository;
import br.com.udemy.springboot.libraryapi.api.service.impl.BookServiceImpl;
import br.com.udemy.springboot.libraryapi.api.model.entity.Book;
import br.com.udemy.springboot.libraryapi.exceptions.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
        Book book = createValidBook();
        when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
        when(repository.save(book)).thenReturn(
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

    private Book createValidBook() {
        return Book.builder()
                .isbn("01234560")
                .autor("Paulo Coelho")
                .title("O Livro do Paulo Coelho")
                .build();
    }

    @Test
    @DisplayName("Deve lancar erro de negocio ao tentar salvar um livro com isbn duplicado")
    public void shouldNotSaveABookWithDuplicatedISBN() {
        //cenario
        Book book = createValidBook();
        when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);
        String mensagemErro = "Isbn já cadastrado.";

        //execucao
        Throwable exception = Assertions.catchThrowable(() -> service.save(book));

        //validacao
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage(mensagemErro);

        verify(repository, never()).save(book);
    }

    @Test
    @DisplayName("Deve obter um livro por Id")
    void getBookById() {

        //cenario
        Long id = 1L;
        Book book = createValidBook();
        book.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(book));

        //execucao
        Optional<Book> foundBook = service.getById(id);

        //validacao
        assertThat( foundBook.isPresent() ).isTrue();
        assertThat( foundBook.get().getId() ).isEqualTo(book.getAutor());
        assertThat( foundBook.get().getAutor() ).isEqualTo(book.getAutor());
        assertThat( foundBook.get().getIsbn() ).isEqualTo(book.getIsbn());
        assertThat( foundBook.get().getTitle() ).isEqualTo(book.getTitle());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar um livro por Id que nao existe na base")
    void bookNotFoundByIdTest() {

        //cenario
        Long id = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //execucao
        Optional<Book> book = service.getById(id);

        //validacao
        assertThat( book.isPresent() ).isFalse();
    }
}
