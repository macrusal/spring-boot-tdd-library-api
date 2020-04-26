package br.com.udemy.springboot.libraryapi.service;

import br.com.udemy.springboot.libraryapi.model.entity.Book;

/**
 * @author macrusal on 26/04/20
 * @project library-api
 */
public interface BookService {

    Book save(Book book);
}
