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

    public List<MediData> findMediData(long id) {
        return entityManager.createQuery("FROM MediData WHERE patient_id = :id ORDER BY measuredDate DESC ")
                .setParameter("id", id)
                .getResultList();
    }
    public List<MediData> findConsultationMedi(long cId){
        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT M.* " +
                "FROM MediData M " +
                "WHERE M.consultation_id = :cId";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("cId", cId);
        query.addEntity(MediData.class);
        return query.getResultList();
    }

    public List<MediData> findMediDataWithNoConsultation(long pId) {

        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT M.* " +
                "FROM MediData M " +
                "INNER JOIN Patient P " +
                "on M.patient_id = P.id WHERE M.patient_id  = :pId AND M.consultation_id is null";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("pId", pId);
        query.addEntity(MediData.class);
        return query.getResultList();
    }

    public List<MediData> findMediDataWithNoConsultation(long pId, long dId) {

        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT M.* " +
                "FROM MediData M " +
                "INNER JOIN Patient P " +
                "on M.patient_id = P.id WHERE M.patient_id  = :pId AND P.doctor_id =:dId AND M.consultation_id is null ORDER BY measuredDate DESC";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("dId", dId);
        query.setParameter("pId", pId);
        query.addEntity(MediData.class);
        return query.getResultList();
    }

    public List<MediData> findMediData(long pid, long d_id) {

        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT M.* " +
                "FROM MediData M " +
                "INNER JOIN Patient P " +
                "on M.patient_id = P.id WHERE M.patient_id  = :pid AND P.doctor_id =:d_id ORDER BY measuredDate DESC";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("d_id", d_id);
        query.setParameter("pid", pid);
        query.addEntity(MediData.class);
        return query.getResultList();
    }

    public List<MediData> findMonitoredMediData(long pid, String startDate, String endDate)
    {
        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT M.* " +
                "from MediData M, Patient P " +
                "where M.patient_id = P.id and P.id = :pid and " +
                "M.measuredDate >= :startDate AND M.measuredDate <= :endDate";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("pid", pid);
        query.setParameter("endDate", endDate);
        query.setParameter("startDate", startDate);
        query.addEntity(MediData.class);
        return query.getResultList();

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

        return Double.parseDouble(value.get(0).toString());
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

    public boolean checkIfReady(long id) {

        Session s = (Session) entityManager.getDelegate();
        String sql = "Select (COUNT(DISTINCT CAST(M.measuredDate AS DATE))) AS Days " +
                "from Patient P, MediData M " +
                "where P.id = M.patient_id and P.id = :id " +
                "and M.measuredDate > (SELECT MAX(C.ConsultationDate) " +
                "FROM Consultation C where C.patient_id = M.patient_id)";

        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("id", id);
        List value = query.list();
        System.out.println(value.get(0));
        double x = Double.parseDouble(value.get(0).toString());
        return (x >= 30);

    }
}

