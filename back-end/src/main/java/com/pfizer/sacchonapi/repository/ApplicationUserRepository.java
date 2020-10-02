package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.Consultation;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.representation.PatientsWithoutConsultationRepresentation;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.restlet.Request;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationUserRepository {
    private EntityManager entityManager;

    public ApplicationUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<ApplicationUser> getCurrent(){
        Request request = Request.getCurrent();
        String username = request.getClientInfo().getUser().getName();
        Optional<ApplicationUser> user = findByUsername(username);
        return user;
    }

    public Optional<ApplicationUser> findByUsername(String username) {
        ApplicationUser userTable = entityManager.find(ApplicationUser.class, username);
        return userTable != null ? Optional.of(userTable) : Optional.empty();
    }

    public List<ApplicationUser> findAllPatients() {
        return entityManager.createQuery("from ApplicationUser WHERE role = 'patient'").getResultList();
    }

    public List<ApplicationUser> findAllDoctors() {
        return entityManager.createQuery("from ApplicationUser WHERE role = 'doctor'").getResultList();
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
                "where P.doctor_id = :did and A.active = 1";


        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("did", did);
        query.addEntity(Patient.class);

        //remove patients that received a consultation in the last month
        List<Patient> patients = query.getResultList();
        List<Patient> finalPatients = new ArrayList<>();

        for (Patient patient : patients) {
            // System.out.println(patient.getApplicationUser().getFirstName());
            List<Consultation> consultations = patient.getApplicationUser().getPatient().getConsultations();
            if (consultations.size() == 0){
                finalPatients.add(patient);
            }
            for (Consultation consultation : consultations) {
                if (((consultation.getConsultationDate().after(before30)) && (consultation.getConsultationDate().before(today))) || consultation.getConsultationDate() == today ) {
                    break;
                } else {
                    finalPatients.add(patient);
                }
            }
        }
        List<Patient> patientsPending = finalPatients.stream()
                .filter(p -> p.isConsultationPending()).collect(Collectors.toList());

        List<ApplicationUser> applicationUsers = patientsPending.stream().
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
        String sql = "SELECT A.* FROM  Doctor D, ApplicationUser A WHERE NOT D.id in (\n" +
                "SELECT C.doctor_id FROM Consultation C\n" +
                "WHERE (C.ConsultationDate>=:fromDate and C.ConsultationDate<=:toDate))\n" +
                "AND A.username = D.user_username";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.addEntity(ApplicationUser.class);
        return query.getResultList();
    }

    public List<ApplicationUser> findInactivePatients(String fromDate, String toDate) {
        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT A.* FROM  Patient P, ApplicationUser A WHERE NOT P.id in (\n" +
                "SELECT M.patient_id FROM MediData M\n" +
                "WHERE (M.measuredDate>=:fromDate and M.measuredDate<=:toDate))\n" +
                "AND A.username = P.user_username";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.addEntity(ApplicationUser.class);
        return query.getResultList();
    }

    public String findByDetails(String username, String password) {
    try {
        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT A.role FROM ApplicationUser A where A.username = :username and A.password = :password and A.active = 1";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("username", username);
        query.setParameter("password", password);

        try {
            String sql2 = "SELECT P.modified FROM Patient P, ApplicationUser A" +
                    " where A.username = :username AND P.user_username = A.username";
            NativeQuery query2 = s.createSQLQuery(sql2);
            query2.setParameter("username", username);

            System.out.println(query.getSingleResult());
            System.out.println(query2.getSingleResult());

            return query.getSingleResult() + "-" + query2.getSingleResult();
        } catch (Exception e) {
            return (String) query.getSingleResult();
        }
    }catch (Exception e) {
        return null;
    }
    }


    public List<PatientsWithoutConsultationRepresentation> WaitingForConsultationAndTimeEllapsed() {

        Session s = (Session) entityManager.getDelegate();
        String sql = "Select P.user_username, (COUNT(DISTINCT CAST(M.measuredDate AS DATE))) AS Days " +
                "from Patient P, MediData M " +
                "where P.id = M.patient_id " +
                "and (M.measuredDate) > (SELECT MAX(C.ConsultationDate) " +
                "FROM Consultation C where C.patient_id = M.patient_id) " +
                "and P.consultationPending = 1" +
                "GROUP BY P.user_username " +
                "HAVING (COUNT(DISTINCT CAST(M.measuredDate AS DATE))) >= 30";
        NativeQuery query = s.createSQLQuery(sql);

        List<Object[]> list = query.getResultList();
        List<PatientsWithoutConsultationRepresentation> patientList = new ArrayList<>();
        String username;
        int daysEllapsed;
        ApplicationUser patient;
        PatientsWithoutConsultationRepresentation modifiedPatient;

        System.out.println("megethos listas" + list.size());

        for(Object[] obj: list)
        {
            username = (String) obj[0];
            daysEllapsed = (int) obj[1];
            System.out.println("Poses" + username + daysEllapsed);
            patient = findByUsername(username).get().getPatient().getApplicationUser();
            System.out.println("Patient" + patient.getFirstName());
            modifiedPatient = new PatientsWithoutConsultationRepresentation(new ApplicationUserRepresentation(patient), daysEllapsed);
            patientList.add(modifiedPatient);
        }

        return patientList;
    }
}
