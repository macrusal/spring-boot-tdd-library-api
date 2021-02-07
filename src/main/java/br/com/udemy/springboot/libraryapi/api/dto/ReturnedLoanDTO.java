package br.com.udemy.springboot.libraryapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author macrusal on 06/02/21
 * @project library-api
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnedLoanDTO {

    private boolean returned;
}
