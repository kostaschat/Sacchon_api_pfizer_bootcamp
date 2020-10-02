package com.pfizer.sacchonapi.representation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientsWithoutConsultationRepresentation {

    private String unconsultedPatient;
    private int timeElapsed;

    public PatientsWithoutConsultationRepresentation( String unconsultedPatient, int timeElapsed) {
        this.unconsultedPatient = unconsultedPatient;
        this.timeElapsed = timeElapsed;
    }
}
