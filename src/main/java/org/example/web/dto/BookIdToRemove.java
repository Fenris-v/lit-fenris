package org.example.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookIdToRemove {
    @NotEmpty
    private String id;
}
