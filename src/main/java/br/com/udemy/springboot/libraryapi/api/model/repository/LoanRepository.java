package br.com.udemy.springboot.libraryapi.api.model.repository;

import br.com.udemy.springboot.libraryapi.api.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author macrusal on 01/02/21
 * @project library-api
 */
public interface LoanRepository extends JpaRepository<Loan,Long> {
}
