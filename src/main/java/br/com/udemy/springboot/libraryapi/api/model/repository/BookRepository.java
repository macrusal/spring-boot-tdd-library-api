package br.com.udemy.springboot.libraryapi.api.model.repository;

import br.com.udemy.springboot.libraryapi.api.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author macrusal on 26/04/20
 * @project library-api
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
}
