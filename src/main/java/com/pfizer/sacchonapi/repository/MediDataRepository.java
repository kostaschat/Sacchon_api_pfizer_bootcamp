package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.MediData;
import lombok.Data;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

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

        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT AVG(" + dataType + ") FROM MEDIDATA WHERE patient_id = :id AND " +
                "measuredDate >= :startDate AND measuredDate <= :endDate";
        NativeQuery query = s.createSQLQuery(sql);
        // query.addEntity(MediData.class);
        query.setParameter("id", id);
        query.setParameter("endDate", endDate);
        query.setParameter("startDate", startDate);
        List value = query.list();
        System.out.println(value.get(0));
        double x = Double.parseDouble(value.get(0).toString());

        return x;

    }

    public Optional<MediData> save(MediData mediData) {
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

