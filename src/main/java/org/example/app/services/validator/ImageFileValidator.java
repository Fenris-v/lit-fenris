package org.example.app.services.validator;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ImageFileValidator implements ConstraintValidator<ValidImage, MultipartFile> {
    private final List<String> allowedTypes = Arrays.asList("image/png", "image/jpg", "image/jpeg");

    @Override
    public boolean isValid(@NotNull MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        String contentType = multipartFile.getContentType();
        return isSupportedContentType(contentType);
    }

    private boolean isSupportedContentType(String contentType) {
        return allowedTypes.contains(contentType);
    }
}
