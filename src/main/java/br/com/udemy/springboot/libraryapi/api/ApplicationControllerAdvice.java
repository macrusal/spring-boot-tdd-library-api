package br.com.udemy.springboot.libraryapi.api;

import br.com.udemy.springboot.libraryapi.api.exceptions.ApiErrors;
import br.com.udemy.springboot.libraryapi.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author macrusal on 31/01/21
 * @project library-api
 */
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinnesExceptions(BusinessException exception) {
        return new ApiErrors(exception);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusExceptions(ResponseStatusException exception) {
        return new ResponseEntity(new ApiErrors(exception), exception.getStatus());
    }
}
