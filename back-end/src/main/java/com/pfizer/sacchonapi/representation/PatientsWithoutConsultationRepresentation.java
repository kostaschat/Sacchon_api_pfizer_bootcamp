package com.pfizer.sacchonapi.representation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientsWithoutConsultationRepresentation {

    private ApplicationUserRepresentation unconsultedPatient;
    private int timeElapsed;

    public PatientsWithoutConsultationRepresentation(ApplicationUserRepresentation unconsultedPatient, int timeElapsed) {
        this.unconsultedPatient = unconsultedPatient;
        this.timeElapsed = timeElapsed;
    }
}
