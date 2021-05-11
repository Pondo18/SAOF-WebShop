package de.hdbw.webshop.util.validation;

import de.hdbw.webshop.annotation.EmailNotUsed;
import de.hdbw.webshop.repository.user.RegisteredUserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailNotUsedValidator implements ConstraintValidator<EmailNotUsed, String> {

    private final RegisteredUserRepository registeredUserRepository;

    public EmailNotUsedValidator(RegisteredUserRepository registeredUserRepository) {
        this.registeredUserRepository = registeredUserRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = registeredUserRepository.findByEmail(email).isEmpty();
        return result;
    }
}
