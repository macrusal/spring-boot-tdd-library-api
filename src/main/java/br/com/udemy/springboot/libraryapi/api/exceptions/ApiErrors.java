package br.com.udemy.springboot.libraryapi.api.exceptions;

import br.com.udemy.springboot.libraryapi.exceptions.BusinessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author macrusal on 26/04/20
 * @project library-api
 */
public class ApiErrors {
    private List<String> errors;

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(erro -> this.errors.add(erro.getDefaultMessage()));
    }

    public ApiErrors(BusinessException exception) {
        this.errors = Arrays.asList(exception.getMessage());
    }

    public ApiErrors(ResponseStatusException exception) {
        this.errors = Arrays.asList(exception.getReason());
    }
    /**
     * Gets errors
     *
     * @return value of errors
     */
    public List<String> getErrors() {
        return errors;
    }
}
