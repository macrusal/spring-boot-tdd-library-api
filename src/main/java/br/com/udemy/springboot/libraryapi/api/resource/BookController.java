package br.com.udemy.springboot.libraryapi.api.resource;

import br.com.udemy.springboot.libraryapi.api.dto.BookDTO;
import br.com.udemy.springboot.libraryapi.api.exceptions.ApiErrors;
import br.com.udemy.springboot.libraryapi.api.model.entity.Book;
import br.com.udemy.springboot.libraryapi.api.service.BookService;
import br.com.udemy.springboot.libraryapi.exceptions.BusinessException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


/**
 * @author macrusal on 25/04/20
 * @project library-api
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;
    private ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper mapper) {
        this.bookService = bookService;
        this.modelMapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto) {

        Book bookEntity = modelMapper.map(dto, Book.class);
        bookEntity = bookService.save(bookEntity);
        BookDTO bookDTO = modelMapper.map(bookEntity, BookDTO.class);

        return bookDTO;
    }

    @GetMapping("{id}")
    public BookDTO get(@PathVariable Long id) {
        return bookService.getById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Book book = bookService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bookService.delete(book);
    }

    @PutMapping("{id}")
    public BookDTO update(@PathVariable Long id, BookDTO bookDTO) {
        return  bookService.getById(id).map(book -> {

            book.setAutor(bookDTO.getAutor());
            book.setTitle(bookDTO.getTitle());
            book = bookService.update(book);
            return modelMapper.map(book, BookDTO.class);

        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinnesExceptions(BusinessException exception) {
        return new ApiErrors(exception);
    }
}
