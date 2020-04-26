package br.com.udemy.springboot.libraryapi.exceptions;

/**
 * @author macrusal on 26/04/20
 * @project library-api
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String s) {
        super(s);
    }
}
