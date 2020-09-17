package com.pfizer.sacchonapi;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.model.Consultation;
import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.repository.ConsultationRepository;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;

import javax.persistence.EntityManager;

public class AddData {
    public static void main(String[] args) throws BadEntityException {
        EntityManager em   = JpaUtil.getEntityManager();
        MediDataRepository mediDataRepository = new MediDataRepository(em);
        ConsultationRepository consultationRepository = new ConsultationRepository(em);
//        DoctorRepository doctorRepository = new DoctorRepository(em);


        MediData m = new MediData();
        m.setCarb(2);
        m.setGlucose(2);
        mediDataRepository.save(m);



        Consultation consultation = new Consultation();
        consultation.setDosage(5);
        consultation.setMedicationName("Therapeia");

        consultationRepository.save(consultation);

//        Doctor doctor = new Doctor();
//        doctor.setUsername("anestis");
//        doctor.setPassword("123");
//        doctor.setRole(Role.ROLE_DOCTOR);
//        doctorRepository.save(doctor);


    }
}

