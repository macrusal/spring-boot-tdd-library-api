package br.com.udemy.springboot.libraryapi.api.service.impl;

import br.com.udemy.springboot.libraryapi.api.model.entity.Loan;
import br.com.udemy.springboot.libraryapi.api.model.repository.LoanRepository;
import br.com.udemy.springboot.libraryapi.api.service.LoanService;
import br.com.udemy.springboot.libraryapi.exceptions.BusinessException;

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
        if(repository.existsByBookAndNotReturned(loan.getBook())) {
            throw new BusinessException("Book already loaned");
        }
        return repository.save(loan);
    }
}
