package br.com.udemy.springboot.libraryapi.api.dto;

import lombok.*;

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
    public String title;
    public String autor;
    public String isbn;

}
