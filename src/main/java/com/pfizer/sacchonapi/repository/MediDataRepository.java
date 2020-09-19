package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.MediData;
import lombok.Data;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Data
public class MediDataRepository {

    private EntityManager entityManager;

    public MediDataRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<MediData> findById(Long id) {
        MediData mediData = entityManager.find(MediData.class, id);
        return mediData != null ? Optional.of(mediData) : Optional.empty();
    }

    public List<MediData> findAll() {
        return entityManager.createQuery("from MediData").getResultList();
    }

    public Optional<MediData> save(MediData mediData){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (mediData);
            entityManager.getTransaction().commit();
            return Optional.of(mediData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<MediData> update(MediData mediData) {

        MediData in = entityManager.find(MediData.class, mediData.getId());
        in.setGlucose(mediData.getGlucose());
        in.setCarb(mediData.getCarb());
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
        Optional<MediData> odata = findById(id);
        if (odata.isPresent()){
            MediData m = odata.get();
            try{
                entityManager.getTransaction().begin();
                entityManager.remove(m);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

