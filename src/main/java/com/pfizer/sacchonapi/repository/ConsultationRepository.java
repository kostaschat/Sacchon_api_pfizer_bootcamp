package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.Consultation;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ConsultationRepository {
    private EntityManager entityManager;

    public ConsultationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Consultation> findById(Long id) {
        Consultation consultation = entityManager.find(Consultation.class, id);
        return consultation != null ? Optional.of(consultation) : Optional.empty();
    }

    public List<Consultation> findAll() {
        return entityManager.createQuery("from Consultation").getResultList();
    }

    public Optional<Consultation> findByName(String name) {
        Consultation consultation = entityManager.createQuery("SELECT b FROM Consultation b WHERE b.medicationName = :medicationName", Consultation.class)
                .setParameter("name", name)
                .getSingleResult();
        return consultation != null ? Optional.of(consultation) : Optional.empty();
    }

    public Optional<Consultation> findByNameNamedQuery(String name) {
        Consultation consultation = entityManager.createNamedQuery("Product.findByName", Consultation.class)
                .setParameter("name", name)
                .getSingleResult();
        return consultation != null ? Optional.of(consultation) : Optional.empty();
    }

    public Optional<Consultation> save(Consultation consultation){

        try {
            entityManager.getTransaction().begin();
            entityManager.persist (consultation);
            entityManager.getTransaction().commit();
            return Optional.of(consultation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<Consultation> update(Consultation consultation) {

        Consultation in = entityManager.find(Consultation.class, consultation.getId());
        in.setMedicationName(consultation.getMedicationName());
        in.setDosage(consultation.getDosage());
        in.setConsultationDate(consultation.getConsultationDate());
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
        Optional<Consultation> oproduct = findById(id);
        if (oproduct.isPresent()){
            Consultation c = oproduct.get();

            try{
                entityManager.getTransaction().begin();
                entityManager.remove(c);
                entityManager.getTransaction().commit();


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }
}
