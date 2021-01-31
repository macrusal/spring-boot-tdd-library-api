package br.com.udemy.springboot.libraryapi.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author macrusal on 31/01/21
 * @project library-api
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    private Long id;
    private String customer;
    private Book book;
    private LocalDate loanDate;
    private Boolean returned;

}
