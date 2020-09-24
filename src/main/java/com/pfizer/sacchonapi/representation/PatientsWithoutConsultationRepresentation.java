package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
