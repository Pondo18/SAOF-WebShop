package de.hdbw.webshop.util.validation;

import de.hdbw.webshop.dto.UserRegistrationFormDTO;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.function.Function;

import static de.hdbw.webshop.util.validation.UserRegistrationFormValidation.ValidationResult.*;

@Deprecated
public interface UserRegistrationFormValidation extends Function<UserRegistrationFormDTO, UserRegistrationFormValidation.ValidationResult> {


    static UserRegistrationFormValidation firstNameValid() {
        return userRegistrationFormDTO -> userRegistrationFormDTO.getFirstName().matches("[a-zA-Z]+") ?
                SUCCESS : FIRST_NAME_CONTAINS_ILLEGAL_CHARACTER;
    }

    static UserRegistrationFormValidation secondNameValid() {
        return userRegistrationFormDTO -> userRegistrationFormDTO.getSecondName().matches("[a-zA-Z]+") ?
                SUCCESS : SECOND_NAME_CONTAINS_ILLEGAL_CHARACTER;
    }

    static UserRegistrationFormValidation emailValid() {
        return userRegistrationFormDTO -> EmailValidator.getInstance().isValid(userRegistrationFormDTO.getEmail()) ?
                SUCCESS : EMAIL_INVALID;
    }

    static UserRegistrationFormValidation passwordMatches() {
        return userRegistrationFormDTO -> userRegistrationFormDTO.getPassword().equals(userRegistrationFormDTO.getRepeatPassword()) ?
                SUCCESS : PASSWORD_NOT_MATCHING;
    }


    @Deprecated
    enum ValidationResult {
        SUCCESS,
        FIRST_NAME_CONTAINS_ILLEGAL_CHARACTER,
        SECOND_NAME_CONTAINS_ILLEGAL_CHARACTER,
        EMAIL_INVALID,
        PASSWORD_NOT_MATCHING
    }
}
