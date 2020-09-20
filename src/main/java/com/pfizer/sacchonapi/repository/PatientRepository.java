package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.Patient;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


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
        return entityManager.createQuery("SELECT p FROM Patient p WHERE p.active = true").getResultList();
    }

    public List<Patient> findPatientsNoConsultation() {
        return entityManager.createQuery("SELECT p FROM Patient p WHERE p.hasConsultation = false").getResultList();
    }

    public List<Patient> findPatientsNoDoctor() {
        return entityManager.createQuery("SELECT p FROM Patient p WHERE p.hasDoctor = false").getResultList();
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

}
