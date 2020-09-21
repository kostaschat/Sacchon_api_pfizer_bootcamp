package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.Doctor;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class DoctorRepository {
    private EntityManager entityManager;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Doctor> findById(Long id) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    public List<Doctor> findAll() {
        return entityManager.createQuery("from Doctor").getResultList();
    }


    public Optional<Doctor> findByName(String user_username) {
        Doctor doctor = entityManager.find(Doctor.class, user_username);
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    public Doctor findByUsername(String user_username) {
        Doctor doctor = entityManager.find(Doctor.class, user_username);
        return doctor;
    }

    public Optional<Doctor> findByNameNamedQuery(String name) {
        Doctor doctor = entityManager.createNamedQuery("Product.findByName", Doctor.class)
                .setParameter("name", name)
                .getSingleResult();
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    public Optional<Doctor> save(Doctor doctor) {

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctor);
            entityManager.getTransaction().commit();
            return Optional.of(doctor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }



    public boolean remove(Long id) {
        Optional<Doctor> oDoctor = findById(id);
        if (oDoctor.isPresent()) {
            Doctor d = oDoctor.get();

            try {
                entityManager.getTransaction().begin();
                entityManager.remove(d);
                entityManager.getTransaction().commit();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}



