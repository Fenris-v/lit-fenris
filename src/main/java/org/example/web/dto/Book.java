package org.example.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Book {
    private Integer id;

    @NotBlank(message = "Author can''t be empty")
    private String author;

    @NotBlank(message = "Title can''t be empty")
    private String title;

    @NotNull(message = "Size can''t be empty")
    @Digits(integer = 4, fraction = 0, message = "Size can''t be more than 9999")
    private Integer size;

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", author='" + author + '\'' + ", title='" + title + '\'' + ", size=" + size + '}';
    }
}
