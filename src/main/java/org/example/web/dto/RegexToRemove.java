package org.example.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.app.services.validator.RemoveRegex;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RegexToRemove {
    @NotBlank(message = "Regex can''t be empty")
    @RemoveRegex(message = "Not valid regex string")
    private String regex;
}
