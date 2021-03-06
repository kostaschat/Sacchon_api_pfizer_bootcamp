package com.pfizer.sacchonapi.resource.Patient;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.resource.MediData.MediDataResourceImpl;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Role;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.logging.Logger;

public class PatientUserListResourceImpl extends ServerResource implements PatientUserListResource {

    public static final Logger LOGGER = Engine.getLogger(MediDataResourceImpl.class);

    private EntityManager em;
    private ApplicationUserRepository applicationUserRepository;

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {

        try {
            em = JpaUtil.getEntityManager();
            applicationUserRepository = new ApplicationUserRepository(em);
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

    }

    @Override
    public <T> List<T> getUsers() throws NotFoundException {

        List<ApplicationUser> applicationUsers;
        List<ApplicationUserRepresentation> result = new ArrayList<>();

        //both of doctors can used this method but for different purposes
        ResourceUtils.checkRoles(this, Shield.chiefDoctor, Shield.doctor);

        Optional<ApplicationUser> user = applicationUserRepository.getCurrent();

        Role role;
        long did;

        if (user.isPresent()) {
            role = user.get().getRole();

        } else {
            LOGGER.config("This doctor cannon be found in the database.");
            throw new NotFoundException("No doctor with this name.");
        }

        System.out.println("here i am " + role);
        if (role.getRoleName().equals("doctor")) {
            did = user.get().getDoctor().getId();
            System.out.println("i am a doctor");
            Date dateToday = new Date();
            Calendar cal = new GregorianCalendar();
            cal.setTime(dateToday);
            cal.add(Calendar.DAY_OF_MONTH, -30);
            Date today30 = cal.getTime();
            applicationUsers = applicationUserRepository.findUnconsultedPatients(did, dateToday, today30);

            applicationUsers.forEach(p -> result.add(new ApplicationUserRepresentation(p)));
        } else if (role.getRoleName().equals("chiefDoctor"))//if he is a chief doctor
        {
            return (List<T>) applicationUserRepository.WaitingForConsultationAndTimeEllapsed();
        }

        return (List<T>) result;
    }

}
