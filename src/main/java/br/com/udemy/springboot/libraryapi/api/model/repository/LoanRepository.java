package br.com.udemy.springboot.libraryapi.api.model.repository;

import br.com.udemy.springboot.libraryapi.api.model.entity.Book;
import br.com.udemy.springboot.libraryapi.api.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author macrusal on 01/02/21
 * @project library-api
 */
public interface LoanRepository extends JpaRepository<Loan,Long> {

    @Query(value =  " select case when ( count(l.id) > 0 ) then true else false end " +
                    "   from Loan l " +
                    "  where l.book = :book " +
                    "    and ( l.returned is null or l.returned is not false )" )
    boolean existsByBookAndNotReturned(@Param("book") Book book);

}
