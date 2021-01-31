package br.com.udemy.springboot.libraryapi.api.service;

import br.com.udemy.springboot.libraryapi.api.model.entity.Loan;

/**
 * @author macrusal on 31/01/21
 * @project library-api
 */
public interface LoanService {

    Loan save(Loan loan);
}
