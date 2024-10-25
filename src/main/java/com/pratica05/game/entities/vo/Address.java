package com.pratica05.game.entities.vo;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Address {

    private String street;
    private String city;
    private String state;

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "Postal code must follow the pattern '#####-###'")
    private String postalCode;

    public Address(String street, String city, String state, String postalCode) {
        this.street = street;
        this.city = city;
        this.state = state;

        if (postalCode == null || !postalCode.matches("\\d{5}-\\d{3}")) {
            throw new IllegalArgumentException("Postal code must follow the pattern '#####-###'");
        }
        this.postalCode = postalCode;
    }
}
