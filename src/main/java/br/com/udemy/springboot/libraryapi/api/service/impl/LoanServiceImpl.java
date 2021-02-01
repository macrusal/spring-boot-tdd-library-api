package br.com.udemy.springboot.libraryapi.api.service.impl;

import br.com.udemy.springboot.libraryapi.api.model.entity.Loan;
import br.com.udemy.springboot.libraryapi.api.model.repository.LoanRepository;
import br.com.udemy.springboot.libraryapi.api.service.LoanService;

/**
 * @author macrusal on 01/02/21
 * @project library-api
 */
public class LoanServiceImpl implements LoanService {

    private LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        return repository.save(loan);
    }
}
