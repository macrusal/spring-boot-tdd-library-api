package br.com.udemy.springboot.libraryapi.service;

import br.com.udemy.springboot.libraryapi.model.entity.Book;
import org.springframework.stereotype.Service;

/**
 * @author macrusal on 26/04/20
 * @project library-api
 */
@Service
public interface BookService {

    Book save(Book book);
}
