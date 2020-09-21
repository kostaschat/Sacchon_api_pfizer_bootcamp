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

    public double average(String startDate, String endDate, String dataType, long id) {

//        Number data;
//        if(dataType.equals("glucose"))
//        {
            System.out.println("THIS IS IT " + id);
          return (double) entityManager.createQuery("SELECT AVG(M.glucose) FROM MediData M where M.patient_id IN :id")
                  .setParameter("id", id)
                  .getSingleResult();


//        }else {
//
//            data = ((Number) entityManager.createQuery("SELECT AVG(M.carb) as avg_carb FROM MediData M " +
//                    "WHERE M.patient_id = :id AND M.measuredDate >= :startDate AND M.measuredDate <= :endDate")
//                    .getSingleResult());
//
//        }

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

