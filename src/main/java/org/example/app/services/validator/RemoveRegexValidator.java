package org.example.app.services.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RemoveRegexValidator implements ConstraintValidator<RemoveRegex, String> {
    @Override
    public boolean isValid(String regex, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            return false;
        }

        return true;
    }
}
