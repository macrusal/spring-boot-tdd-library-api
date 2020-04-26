package br.com.udemy.springboot.libraryapi.api.resource;

import br.com.udemy.springboot.libraryapi.api.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author macrusal on 25/04/20
 * @project library-api
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create() {
        BookDTO dto = new BookDTO();
        dto.setAutor("Autor");
        dto.setTitle("Meu Livro");
        dto.setIsbn("123456");
        dto.setId(1L);
        return dto;
    }
}
