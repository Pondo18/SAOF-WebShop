package de.hdbw.webshop.dto.registration;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ArtistRegistrationFormDTO {

    @NotNull
    private String country;

    @NotNull
    private String domicile;
}
