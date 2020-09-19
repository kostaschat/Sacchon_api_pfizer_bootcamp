package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.resource.PatientResource;
import lombok.Data;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Data
public class PatientRepository {

    private EntityManager entityManager;

    public PatientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Patient> findById(Long id) {
        Patient patient = entityManager.find(Patient.class, id);
        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    public List<Patient> findAll() {
        return entityManager.createQuery("from Patient").getResultList();
    }

    public Optional<Patient> save(Patient patient){

        try {
            entityManager.getTransaction().begin();
            entityManager.persist (patient);
            entityManager.getTransaction().commit();
            return Optional.of(patient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<Patient> update(Patient patient) {

        Patient in = entityManager.find(Patient.class, patient.getId());
        in.setFirstName(patient.getFirstName());
        in.setLastName(patient.getLastName());
        in.setUsername(patient.getUsername());

        in.setEmail(patient.getEmail());
        in.setPassword(patient.getPassword());
        in.setAddress(patient.getAddress());
        in.setCity(patient.getCity());
        in.setZipCode(patient.getZipCode());
        in.setPhoneNumber(patient.getPhoneNumber());
        in.setDob(patient.getDob());
        in.setCreationDate(patient.getCreationDate());

        in.setActive(patient.isActive());
        in.setHasConsultation(patient.isHasConsultation());
        in.setConsultationPending(patient.isConsultationPending());
        in.setHasDoctor(patient.isHasDoctor());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist (in);
            entityManager.getTransaction().commit();
            return Optional.of(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean remove(Long id){
        Optional<Patient> odata = findById(id);
        if (odata.isPresent()){
            Patient m = odata.get();
            m.setActive(false);
        }
        return true;
    }
}
