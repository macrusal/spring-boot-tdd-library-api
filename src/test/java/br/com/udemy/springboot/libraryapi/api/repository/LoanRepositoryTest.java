package br.com.udemy.springboot.libraryapi.api.repository;

import br.com.udemy.springboot.libraryapi.api.dto.BookDTO;
import br.com.udemy.springboot.libraryapi.api.model.entity.Book;
import br.com.udemy.springboot.libraryapi.api.model.entity.Loan;
import br.com.udemy.springboot.libraryapi.api.model.repository.LoanRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.LocalDate;

/**
 * @author macrusal on 06/02/21
 * @project library-api
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deve verificar se existe um emprestimo não devolvido para um livro ")
    public void existsByBookAndNotReturnedTest() {

        //cenario
        Book book = createNewBook("1234");
        entityManager.persist(book);

        Loan loan = Loan.builder().book(book).customer("Marcelo").loanDate(LocalDate.now()).build();
        entityManager.persist(loan);

        //execucao
        boolean exists = repository.existsByBookAndNotReturned(book);

        assertThat(exists).isTrue();

    }

    private Book createNewBook(String isbn) {
        return Book.builder()
                .autor("Autor desconhecido")
                .title("O Livro dos Segredos")
                .isbn(isbn)
                .build();
    }
}
