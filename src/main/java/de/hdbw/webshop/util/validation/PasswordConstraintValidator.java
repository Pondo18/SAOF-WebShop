package de.hdbw.webshop.util.validation;


import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.hdbw.webshop.annotation.ValidPassword;

import org.passay.*;

import com.google.common.base.Joiner;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(final ValidPassword arg0) {

    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        final PasswordValidator validator = new PasswordValidator(Arrays.asList(
//                new LengthRule(8, 30),
//                new CharacterRule(EnglishCharacterData.UpperCase, 1),
//                new CharacterRule(EnglishCharacterData.LowerCase, 1),
//                new CharacterRule(EnglishCharacterData.Digit, 1),
//                new CharacterRule(EnglishCharacterData.Special, 1),
//                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
//                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
//                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                new WhitespaceRule()));
        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result))).addConstraintViolation();
        return false;
    }

}
