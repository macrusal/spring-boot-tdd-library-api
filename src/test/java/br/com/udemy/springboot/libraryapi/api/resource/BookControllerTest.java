package br.com.udemy.springboot.libraryapi.api.resource;

import br.com.udemy.springboot.libraryapi.api.dto.BookDTO;
import br.com.udemy.springboot.libraryapi.api.model.entity.Book;
import br.com.udemy.springboot.libraryapi.api.service.BookService;
import br.com.udemy.springboot.libraryapi.exceptions.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author macrusal on 25/04/20
 * @project library-api
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    static String BOOK_API = "/api/books";

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService bookService;

    @Test
    @DisplayName("Deve criar um livro com sucesso")
    public void createBookTest() throws Exception {
        BookDTO dto = createNewBook();

        Book savedBook = Book.builder()
                .autor("Autor desconhecido")
                .title("O Livro dos Segredos")
                .isbn("01234560")
                .id(1L)
                .build();

        BDDMockito.given(bookService.save(Mockito.any(Book.class))).willReturn(savedBook);
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").value(1L))
            .andExpect(jsonPath("title").value(dto.getTitle()))
            .andExpect(jsonPath("autor").value(dto.getAutor()))
            .andExpect(jsonPath("isbn").value(dto.getIsbn()));
    }

    @Test
    @DisplayName("Deve lancar erro de validacao qdo nao houver dados suficiente para criar um livro")
    public void createInvalidBookTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath("errors", hasSize(3)));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn ja utilizado por outro livro")
    public void createBookWithDuplicatedISBN() throws Exception {
        BookDTO dto = createNewBook();
        String json = new ObjectMapper().writeValueAsString(dto);
        String mensagemErro = "Isbn já cadastrado.";
        BDDMockito.given(bookService.save(Mockito.any(Book.class)))
                .willThrow(new BusinessException(mensagemErro));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(mensagemErro));
    }

    @Test
    @DisplayName("Deve obter as informações de um livro")
    void getBookDetailsTest() throws Exception {
        //cenario
        Long id = 1L;
        Book book = Book.builder()
                .id(id)
                .autor(createNewBook().getAutor())
                .title(createNewBook().getTitle())
                .isbn(createNewBook().getIsbn())
                .build();
        BDDMockito.given(bookService.getById(id)).willReturn(Optional.of(book));

        //execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BOOK_API.concat("/"+id))
                .accept(MediaType.APPLICATION_JSON);

        //verificacao
        mvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(id))
            .andExpect(jsonPath("title").value(createNewBook().getTitle()))
            .andExpect(jsonPath("autor").value(createNewBook().getAutor()))
            .andExpect(jsonPath("isbn").value(createNewBook().getIsbn()));

    }

    @Test
    @DisplayName("Deve retornar resource not found quando o livro não existir")
    public void bookNotFoundTest() throws Exception {
        //cenario
        Long id = 1L;

        BDDMockito.given(bookService.getById(anyLong())).willReturn(Optional.empty());

        //execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BOOK_API.concat("/"+id))
                .accept(MediaType.APPLICATION_JSON);

        //verificacao
        mvc
            .perform(request)
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve excluir um livro")
    public void deleteBookTest() throws Exception {
        //cenario
        Long id = 1L;
        BDDMockito.given(bookService.getById(anyLong())).willReturn(Optional.of(Book.builder().id(id).build()));

        //execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(BOOK_API.concat("/"+id));

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar resource not found quando nao houver um livro para deletar")
    public void deleteDoesNotExistsBookTest() throws Exception {
        //cenario
        Long id = 1L;
        BDDMockito.given(bookService.getById(anyLong())).willReturn(Optional.empty());

        //execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(BOOK_API.concat("/"+id));

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar um livro")
    public void updateBookTest() throws Exception {
        //cenario
        Long id = 1L;
        String json = new ObjectMapper().writeValueAsString(createNewBook());
        Book updatingBook = Book.builder()
                .id(id)
                .autor("Jhonson Mayer")
                .title("The Judge")
                .isbn("01234560")
                .build();

        BDDMockito.given(bookService.getById(id))
                .willReturn(Optional.of(updatingBook));

        Book updatedBook = Book.builder()
                .id(id)
                .autor("Autor desconhecido")
                .title("O Livro dos Segredos")
                .isbn("01234560")
                .build();
        BDDMockito.given(bookService.update(updatingBook))
                .willReturn(updatedBook);

        //execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BOOK_API.concat("/"+id))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("autor").value(createNewBook().getAutor()))
                .andExpect(jsonPath("title").value(createNewBook().getTitle()))
                .andExpect(jsonPath("isbn").value(createNewBook().getIsbn()));
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentat atualizar um livro inexistente")
    void updateDoesNotExistsBookTest() throws Exception {
        //cenario
        Long id = 1L;
        String json = new ObjectMapper().writeValueAsString(createNewBook());
        BDDMockito.given(bookService.getById(anyLong()))
                .willReturn(Optional.empty());

        //execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BOOK_API.concat("/"+id))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve filtrar livro")
    void findBookTest() throws Exception {

        Long id = 1L;

        Book book = Book.builder()
                    .id(id)
                    .title(createNewBook().getTitle())
                    .autor(createNewBook().getAutor())
                    .isbn(createNewBook().getIsbn())
                    .build();

        BDDMockito.given(bookService.find(Mockito.any(Book.class), Mockito.any(Pageable.class)))
                .willReturn( new PageImpl<Book>(Arrays.asList(book), PageRequest.of(0, 100), 1));

        String queryString = String.format("?title=%s&author=%s&page=0&size=100",
                book.getTitle(), book.getAutor());

        //execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BOOK_API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(1)))
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("pageable.pageSize").value(100))
                .andExpect(jsonPath("pageable.pageNumber").value(0))
        ;

    }

    private BookDTO createNewBook() {
        return BookDTO.builder()
                .autor("Autor desconhecido")
                .title("O Livro dos Segredos")
                .isbn("01234560")
                .build();
    }
}
