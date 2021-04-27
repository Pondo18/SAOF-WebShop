package de.hdbw.webshop.primary.validation;

import de.hdbw.webshop.primary.dto.UserRegistrationFormDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return UserRegistrationFormDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object obj, final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "message.firstName", "First Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "message.lastName", "Last Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "message.password", "Last Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "message.username", "UserName is required.");
    }

}
