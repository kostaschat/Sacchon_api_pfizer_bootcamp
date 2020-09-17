package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.Doctor;

import javax.persistence.EntityManager;
import java.util.Optional;

public class DoctorRepository {
    private EntityManager entityManager;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Doctor> save(Doctor doctor){

        try {
            entityManager.getTransaction().begin();
            entityManager.persist (doctor);
            entityManager.getTransaction().commit();
            return Optional.of(doctor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
