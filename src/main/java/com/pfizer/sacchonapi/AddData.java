package com.pfizer.sacchonapi;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Consultation;
import com.pfizer.sacchonapi.model.Doctor;
import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.ConsultationRepository;
import com.pfizer.sacchonapi.repository.DoctorRepository;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.security.Role;
import org.restlet.security.User;

import javax.persistence.EntityManager;

public class AddData {
    public static void main(String[] args) throws BadEntityException {
        EntityManager em   = JpaUtil.getEntityManager();
        MediDataRepository mediDataRepository = new MediDataRepository(em);
        ConsultationRepository consultationRepository = new ConsultationRepository(em);
//        DoctorRepository doctorRepository = new DoctorRepository(em);

//
//        MediData m = new MediData();
//        m.setCarb(2);
//        m.setGlucose(2);
//        mediDataRepository.save(m);
//
//
//
//        Consultation consultation = new Consultation();
//        consultation.setDosage(5);
//        consultation.setMedicationName("Therapeia");
//
//        consultationRepository.save(consultation);

        ApplicationUser user = new ApplicationUser();
        ApplicationUserRepository user_repo = new ApplicationUserRepository(em);
        DoctorRepository doctorRepo = new DoctorRepository(em);
        user.setUsername("paro8");
        user.setPassword("padof");
        user.setRole(Role.doctor);
        user.setFirstName("loukas");
        user.setLastName("Loukidis");
        user_repo.save(user);
        Doctor doctor = new Doctor();
        doctor.setApplicationUser(user);
        doctorRepo.save(doctor);



    }
}

