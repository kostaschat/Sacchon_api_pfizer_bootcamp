package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.ChiefDoctor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ChiefDoctorRepository {
    private EntityManager entityManager;

    public ChiefDoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<ChiefDoctor> findById(Long id) {
        ChiefDoctor chiefDoctor = entityManager.find(ChiefDoctor.class, id);
        return chiefDoctor != null ? Optional.of(chiefDoctor) : Optional.empty();
    }

    public Optional<ChiefDoctor> save(ChiefDoctor chiefDoctor){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (chiefDoctor);
            entityManager.getTransaction().commit();
            return Optional.of(chiefDoctor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}