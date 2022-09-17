package org.example.app.services.validator;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullFileValidator implements ConstraintValidator<NotNullFile, MultipartFile> {
    @Override
    public boolean isValid(@NotNull MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return !multipartFile.isEmpty();
    }
}
