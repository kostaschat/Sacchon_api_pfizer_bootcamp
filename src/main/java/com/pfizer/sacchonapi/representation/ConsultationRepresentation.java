package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.Consultation;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@NoArgsConstructor
public class ConsultationRepresentation {
    private String medicationName;
    private double dosage;
    private Date consultationDate;
    private String advice;
    private long doctor_id;
    private long patient_id;
    private long id;


    public ConsultationRepresentation(Consultation consultation) {
        if (consultation != null) {
            medicationName = consultation.getMedicationName();
            dosage = consultation.getDosage();
            consultationDate = consultation.getConsultationDate();
            advice = consultation.getAdvice();
            doctor_id = consultation.getDoctor().getId();
            patient_id = consultation.getPatient().getId();
            id = consultation.getId();
        }
    }

    public Consultation createConsulation() {
        Consultation consultation = new Consultation();
        consultation.setMedicationName(medicationName);
        consultation.setDosage(dosage);
        consultation.setAdvice(advice);
        consultation.setConsultationDate(consultationDate);
        return consultation;
    }
}
