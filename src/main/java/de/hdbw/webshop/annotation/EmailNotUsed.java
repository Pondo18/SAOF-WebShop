package de.hdbw.webshop.annotation;

import de.hdbw.webshop.util.validation.EmailNotUsedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = EmailNotUsedValidator.class)
@Documented
public @interface EmailNotUsed {

    String message() default "The email address is already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
