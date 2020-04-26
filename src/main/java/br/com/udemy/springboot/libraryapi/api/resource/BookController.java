package br.com.udemy.springboot.libraryapi.api.resource;

import br.com.udemy.springboot.libraryapi.api.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
        dto.setAutor("Autor desconhecido");
        dto.setTitle("O Livro dos Segredos");
        dto.setIsbn("01234560");
        dto.setId(1L);
        return dto;
    }
}
