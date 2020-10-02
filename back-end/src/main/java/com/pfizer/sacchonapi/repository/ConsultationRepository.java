package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.Consultation;
import com.pfizer.sacchonapi.model.MediData;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

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

    public List<Consultation> findPatientCons(long id) {

        return entityManager.createQuery("FROM Consultation WHERE patient_id = :id ORDER BY ConsultationDate DESC")
                .setParameter("id", id)
                .getResultList();
    }

    public List<Consultation> findPatientCons(long p_id, long d_id) {

        return entityManager.createQuery("FROM Consultation WHERE patient_id = :p_id AND doctor_id = :d_id ORDER BY ConsultationDate DESC")
                .setParameter("p_id", p_id)
                .setParameter("d_id", d_id)
                .getResultList();
    }

    public boolean findCons(long p_id) {
        List<Consultation> consultations = entityManager.createQuery("FROM Consultation WHERE patient_id = :p_id")
                .setParameter("p_id", p_id)
                .getResultList();

        Session s = (Session) entityManager.getDelegate();
        String sql = "Select (COUNT(DISTINCT CAST(M.measuredDate AS DATE))) AS Days " +
                "from Patient P, MediData M " +
                "where P.id = M.patient_id and P.id = :p_id";

        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("p_id", p_id);
        List value = query.list();
        System.out.println(value.get(0));
        double x = Double.parseDouble(value.get(0).toString());

        if ((consultations.size() == 0) && (x>=30))
         return true;

        return false;
    }

//    public List<Consultation> findAll(Date startDate, Date endDate) {
//        return entityManager.createQuery("SELECT c FROM Consultation " +
//                "WHERE c.ConsultationDate >= :startDate AND c.ConsultationDate <= :endDATE").getResultList();
//    }

    public Optional<Consultation> findByName(String medicationName) {
        Consultation consultation = entityManager.createQuery("SELECT b FROM Consultation b WHERE b.medicationName = :medicationName", Consultation.class)
                .setParameter("medicationName", medicationName)
                .getSingleResult();
        return consultation != null ? Optional.of(consultation) : Optional.empty();
    }

    public Optional<Consultation> findByNameNamedQuery(String medicationName) {
        Consultation consultation = entityManager.createNamedQuery("Consultation.findByName", Consultation.class)
                .setParameter("medicationName", medicationName)
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
        in.setAdvice(consultation.getAdvice());
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
        Optional<Consultation> oconsultation = findById(id);
        if (oconsultation.isPresent()){
            Consultation c = oconsultation.get();
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

    public List<Consultation> findMonitoredConsultations(long did, String startDate, String endDate) {

        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT C.* " +
                "from Consultation C, Doctor D " +
                "where C.doctor_id = D.id and D.id = :did and " +
                "ConsultationDate >= :startDate AND ConsultationDate <= :endDate";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("did", did);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.addEntity(Consultation.class);
        return query.getResultList();
    }
}
