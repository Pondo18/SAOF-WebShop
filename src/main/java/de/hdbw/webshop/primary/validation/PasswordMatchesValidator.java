package de.hdbw.webshop.primary.validation;

import de.hdbw.webshop.primary.annotation.PasswordMatches;
import de.hdbw.webshop.primary.dto.UserRegistrationFormDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserRegistrationFormDTO user = (UserRegistrationFormDTO) obj;
        return user.getPassword().equals(user.getRepeatPassword());
    }

}
