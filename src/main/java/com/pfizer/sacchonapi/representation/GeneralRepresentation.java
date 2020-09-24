package com.pfizer.sacchonapi.representation;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralRepresentation {

    private String message;

    public GeneralRepresentation(String message) {
        this.message = message;
    }
}
