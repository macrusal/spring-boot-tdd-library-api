package br.com.udemy.springboot.libraryapi.api.service.impl;

import br.com.udemy.springboot.libraryapi.api.model.repository.BookRepository;
import br.com.udemy.springboot.libraryapi.api.model.entity.Book;
import br.com.udemy.springboot.libraryapi.api.service.BookService;
import org.springframework.stereotype.Service;

/**
 * @author macrusal on 26/04/20
 * @project library-api
 */
@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {

        return repository.save(book);
    }
}
