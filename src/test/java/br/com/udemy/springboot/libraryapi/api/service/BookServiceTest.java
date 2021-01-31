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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
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
    public void getBookById() {

        //cenario
        Long id = 1L;
        Book book = createValidBook();
        book.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(book));

        //execucao
        Optional<Book> foundBook = service.getById(id);

        //validacao
        assertThat( foundBook.isPresent() ).isTrue();
        assertThat( foundBook.get().getId() ).isEqualTo(book.getId());
        assertThat( foundBook.get().getAutor() ).isEqualTo(book.getAutor());
        assertThat( foundBook.get().getIsbn() ).isEqualTo(book.getIsbn());
        assertThat( foundBook.get().getTitle() ).isEqualTo(book.getTitle());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar um livro por Id que nao existe na base")
    public void bookNotFoundByIdTest() {

        //cenario
        Long id = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //execucao
        Optional<Book> book = service.getById(id);

        //validacao
        assertThat( book.isPresent() ).isFalse();
    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void deleteBookTest() {
        //cenario
        Long id = 1L;
        Book book = createValidBook();
        book.setId(id);

        //execucao
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> service.delete(book));

        //validacao
        Mockito.verify(repository, Mockito.times(1)).delete(book);
    }

    @Test
    @DisplayName("Deve lancar exceção ao tentar deletar um livro sem id")
    public void deleteInvalidBookTest() {
        //cenario
        Book book = new Book();

        //execucao
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(book));

        //validacao
        Mockito.verify(repository, Mockito.never()).delete(book);
    }

    @Test
    @DisplayName("Deve atualizar um livro")
    public void updateBookTest() {
        //cenario
        Book updatingBook = Book.builder().id(1L).build();

        Book updatedBook = createValidBook();
        updatedBook.setId(1L);
        Mockito.when(repository.save(updatingBook)).thenReturn(updatedBook);

        //execucao
        Book  book = service.save(updatingBook);
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> service.update(book));

        //validacao
        Mockito.verify(repository, Mockito.times(1)).save(book);
        assertThat(book.getId()).isEqualTo(updatedBook.getId());
        assertThat(book.getAutor()).isEqualTo(updatedBook.getAutor());
        assertThat(book.getIsbn()).isEqualTo(updatedBook.getIsbn());
        assertThat(book.getTitle()).isEqualTo(updatedBook.getTitle());
    }

    @Test
    @DisplayName("Deve lancar exceção ao tentar atualizar um livro sem id")
    public void updateInvalidBookTest() {
        //cenario
        Book book = new Book();

        //execucao
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(book));

        //validacao
        Mockito.verify(repository, Mockito.never()).save(book);
    }


    @Test
    @DisplayName("Deve filtrar um livro pelas propriedades")
    void findBookByPropertiesTest() {

        //cenario
        Book book = createValidBook();
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Book> lista = Arrays.asList(book);
        Page<Book> page= new PageImpl<Book>(lista, pageRequest, 1);
        Mockito.when(repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class))).thenReturn(page);

        //execucao
        Page<Book> result = service.find(book, pageRequest);

        //verificacoes
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(lista);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);

    }
}
