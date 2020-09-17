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

    private String uri;

    public ConsultationRepresentation(
            Consultation consultation) {
        if (consultation != null) {
            medicationName = consultation.getMedicationName();
            dosage = consultation.getDosage();
            consultationDate = consultation.getConsultationDate();
            uri = "http://localhost:9000/consulation/" + consultation.getId();
        }
    }
    public Consultation createConsulation() {
        Consultation consultation = new Consultation();
        consultation.setMedicationName(medicationName);
        consultation.setDosage(dosage);
        consultation.setConsultationDate(consultationDate);
        return consultation;
    }
}
