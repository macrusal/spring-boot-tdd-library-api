package br.com.udemy.springboot.libraryapi.api.service;

import br.com.udemy.springboot.libraryapi.api.model.entity.Book;

import java.util.Optional;

/**
 * @author macrusal on 26/04/20
 * @project library-api
 */
public interface BookService {

    Book save(Book book);

    Optional<Book> getById(Long id);

    void delete(Book book);

    Book update(Book book);
}
