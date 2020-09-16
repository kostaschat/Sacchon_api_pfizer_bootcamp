package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.Consultation;

import java.util.Date;


public class ConsulationRepresentation {
    private String medicationName;
    private double dosage;
    private Date consultationDate;

    private String uri;

    public ConsulationRepresentation(
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
