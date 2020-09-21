package com.pfizer.sacchonapi.repository;

import com.pfizer.sacchonapi.model.ApplicationUser;

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
        return entityManager.createQuery("SELECT ApplicationUser.active FROM ApplicationUser" +
                "INNER JOIN Patient ON Patient.Username = ApplicationUser.Username" +
                "  ").getResultList();
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
