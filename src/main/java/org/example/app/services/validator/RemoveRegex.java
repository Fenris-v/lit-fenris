package org.example.app.services.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RemoveRegexValidator.class})
public @interface RemoveRegex {
    String message() default "Invalid regex";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
