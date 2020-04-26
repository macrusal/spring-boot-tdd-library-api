package br.com.udemy.springboot.libraryapi.api.resource;

import br.com.udemy.springboot.libraryapi.api.dto.BookDTO;
import br.com.udemy.springboot.libraryapi.model.entity.Book;
import br.com.udemy.springboot.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * @author macrusal on 25/04/20
 * @project library-api
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO dto) {

        Book bookEntity = Book.builder()
                .autor(dto.getAutor())
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .build();
        bookEntity = bookService.save(bookEntity);

        BookDTO bookDTO = BookDTO.builder()
                .id(bookEntity.id)
                .autor(bookEntity.getAutor())
                .title(bookEntity.getTitle())
                .isbn(bookEntity.getIsbn())
                .build();

        return bookDTO;
    }
}
