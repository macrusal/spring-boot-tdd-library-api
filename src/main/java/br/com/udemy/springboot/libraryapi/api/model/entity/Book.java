package br.com.udemy.springboot.libraryapi.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author macrusal on 26/04/20
 * @project library-api
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    public Long id;
    public String title;
    public String autor;
    public String isbn;
}
