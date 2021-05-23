package de.hdbw.webshop.util.validation;

import de.hdbw.webshop.annotation.PasswordMatches;
import de.hdbw.webshop.dto.registration.UserRegistrationFormDTO;

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
