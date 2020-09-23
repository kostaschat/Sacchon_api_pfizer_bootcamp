package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Patient;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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

    public Optional<ApplicationUser> save(ApplicationUser applicationUser){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (applicationUser);
            entityManager.getTransaction().commit();
            return Optional.of(applicationUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
