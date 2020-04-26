package br.com.udemy.springboot.libraryapi.api.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * @author macrusal on 25/04/20
 * @project library-api
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    public Long id;

    @NotEmpty
    public String title;

    @NotEmpty
    public String autor;

    @NotEmpty
    public String isbn;

}
