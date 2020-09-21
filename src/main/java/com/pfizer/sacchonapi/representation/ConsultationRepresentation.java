package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.Consultation;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.ConsultationRepository;
import com.pfizer.sacchonapi.repository.DoctorRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.jetty.client.api.Request;
import org.restlet.security.User;

import java.util.Date;

@Data
@NoArgsConstructor
public class ConsultationRepresentation {
    private String medicationName;
    private double dosage;
    private Date consultationDate;
    private long doctor_id;
    private long patient_id;
    private String uri;


    public ConsultationRepresentation(Consultation consultation) {
        if (consultation != null) {
            medicationName = consultation.getMedicationName();
            dosage = consultation.getDosage();
            consultationDate = consultation.getConsultationDate();
            doctor_id = consultation.getDoctor().getId();
            patient_id = consultation.getPatient().getId();
            uri = "http://localhost:9000/consultation/" + consultation.getId();
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
