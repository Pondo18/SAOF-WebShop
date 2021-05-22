package de.hdbw.webshop.dto.registration;

import de.hdbw.webshop.annotation.EmailNotUsed;
import de.hdbw.webshop.annotation.PasswordMatches;
import de.hdbw.webshop.annotation.ValidEmail;
import de.hdbw.webshop.annotation.ValidPassword;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@PasswordMatches
public class UserRegistrationFormDTO {

    @NotNull
    @Size(min = 3)
    private String firstName;

    @NotNull
    @Size(min = 3)
    private String secondName;

    @NotNull
    @ValidEmail
    @EmailNotUsed
    private String email;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String repeatPassword;
}
