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

    public List<ApplicationUser> findDoctorsPatients(long did) {

        Session s = (Session) entityManager.getDelegate();
        String sql = "SELECT *" +
                "from ApplicationUser A " +
                "INNER JOIN Patient P " +
                "on A.active = false and A.username = P.user_username where doctor_id  = :did or doctor_id = null";
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameter("did", did);
        return query.list();
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
