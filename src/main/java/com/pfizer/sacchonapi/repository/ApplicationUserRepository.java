package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Consultation;
import com.pfizer.sacchonapi.model.Doctor;
import com.pfizer.sacchonapi.model.Patient;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationUserRepository {
    private EntityManager entityManager;

    public ApplicationUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<ApplicationUser> findByUsername(String username) {
        ApplicationUser userTable = entityManager.find(ApplicationUser.class, username);
        return userTable != null ? Optional.of(userTable) : Optional.empty();
    }

    public List<ApplicationUser> findAll() {
        return entityManager.createQuery("from ApplicationUser").getResultList();
    }

    public List<ApplicationUser> findAvailablePatients() {

        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT A.*" +
                "from ApplicationUser A " +
                "INNER JOIN Patient P " +
                "on A.username = P.user_username where P.doctor_id is null and A.active = 1" +
                "ORDER BY A.creationDate DESC";
        NativeQuery query = s.createSQLQuery(sql);
        query.addEntity(ApplicationUser.class);
        return query.getResultList();
    }

    public List<ApplicationUser> findMyPatients(long did) {

        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT A.*" +
                "from ApplicationUser A " +
                "INNER JOIN Patient P " +
                "on A.username = P.user_username where P.doctor_id  = :did and A.active = 1";

        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("did", did);
        query.addEntity(ApplicationUser.class);
        return query.getResultList();
    }

    public List<ApplicationUser> findUnconsultedPatients(long did, Date today, Date before30) {

        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT P.*  from ApplicationUser A " +
                "INNER JOIN Patient P " +
                "ON A.username = P.user_username " +
                "INNER JOIN Consultation C " +
                "ON C.patient_id = P.id where P.doctor_id = :did and A.active = 1";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("did", did);
        query.addEntity(Patient.class);

        //remove patients that received a consultation in the last month
        List<Patient> patients = query.getResultList();
        List<Patient> finalPatients = new ArrayList<>();
        boolean getOut = false;


        for (Patient patient : patients) {
            // System.out.println(patient.getApplicationUser().getFirstName());
            List<Consultation> consultations = patient.getApplicationUser().getPatient().getConsultations();

            for (Consultation consultation : consultations) {
                if ((consultation.getConsultationDate().after(before30)) && (consultation.getConsultationDate().before(today)) || consultation.getConsultationDate() == today) {
                    getOut = true;
                    break;
                } else {
                    finalPatients.add(patient);
                }
            }
        }

        List<ApplicationUser> applicationUsers = finalPatients.stream().
                map(applicationUser -> new ApplicationUser(applicationUser.getApplicationUser().getPatient().getApplicationUser()))
                .collect(Collectors.toList());

        return new ArrayList<>(new HashSet(applicationUsers));
    }

    public Optional<ApplicationUser> save(ApplicationUser applicationUser) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(applicationUser);
            entityManager.getTransaction().commit();
            return Optional.of(applicationUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<ApplicationUser> findInactiveDoctors(String fromDate, String toDate) {
        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT DISTINCT A.* from ApplicationUser A\n" +
                " INNER JOIN Doctor D\n" +
                " ON A.username = D.user_username\n" +
                " INNER JOIN Consultation C\n" +
                " ON C.doctor_id = D.id where A.active = 1 AND C.ConsultationDate NOT IN(\n" +
                "SELECT C.ConsultationDate \n" +
                "WHERE (C.ConsultationDate > :fromDate AND C.ConsultationDate < :toDate))";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.addEntity(ApplicationUser.class);
        return query.getResultList();

    }

    public List<ApplicationUser> findInactivePatients(String fromDate, String toDate) {
        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT DISTINCT A.* from ApplicationUser A\n" +
                "INNER JOIN Patient P\n" +
                "ON A.username = P.user_username\n" +
                "INNER JOIN MediData M\n" +
                "ON M.patient_id = P.id where A.active = 1 AND M.measuredDate NOT IN(\n" +
                "SELECT M.measuredDate\n" +
                "WHERE (M.measuredDate > :fromDate AND M.measuredDate < :toDate))";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.addEntity(ApplicationUser.class);
        return query.getResultList();

    }

    public boolean findByDetails(String username, String password) {

        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT A.* FROM ApplicationUser A where A.username = :username and A.password = :password";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("username", username);
        query.setParameter("password", password);

        try{
            query.getSingleResult();
            return true;
        } catch (Exception e){
            return false;
        }

    }
}
