package com.pfizer.sacchonapi.resource.ChiefDoctor;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ChiefDoctorListResourceImpl extends ServerResource implements ChiefDoctorListResource {

    public static final Logger LOGGER = Engine.getLogger(ChiefDoctorListResource.class);
    private ApplicationUserRepository applicationUserRepository;
    private EntityManager em;

    private String fromDate;
    private String toDate;

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising medical data resource starts");
        try {
            em = JpaUtil.getEntityManager();
            applicationUserRepository = new ApplicationUserRepository(em);
            fromDate = getAttribute("fromdate");
            toDate = getAttribute("todate");

        } catch (Exception e) {
            fromDate = null;
            toDate = null;
            LOGGER.info(e.getMessage());
        }


        LOGGER.info("Initialising patient data resource ends");
    }

    @Override
    public List<ApplicationUserRepresentation> getDoctors() throws NotFoundException {
        LOGGER.info("Retrieve Doctors");

        ResourceUtils.checkRole(this, Shield.chiefDoctor);

        try {
            List<ApplicationUser> applicationUsers = applicationUserRepository.findInactiveDoctors(fromDate,toDate);
            List<ApplicationUserRepresentation> result = new ArrayList<>();

            applicationUsers.forEach(user -> result.add(new ApplicationUserRepresentation(user)));
            return result;
        } catch (Exception e) {
            throw new NotFoundException("doctors not found");
        }
    }
}
