package org.example.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class BookIdToRemove {
    @NotNull(message = "Id can''t be empty")
    @Positive(message = "Id can''t be 0 or negative")
    private Integer id;
}
