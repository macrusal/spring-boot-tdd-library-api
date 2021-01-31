package br.com.udemy.springboot.libraryapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author macrusal on 31/01/21
 * @project library-api
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoandDTO {

    private String isbn;
    private String customer;
}
