package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ChiefDoctor;
import com.pfizer.sacchonapi.repository.ChiefDoctorRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ChiefDoctorRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class ChiefDoctorResourceImpl extends ServerResource implements ChiefDoctorResource{
    public static final Logger LOGGER = Engine.getLogger(ChiefDoctorResourceImpl.class);

    private long id;
    private ChiefDoctorRepository chiefDoctorRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising Chief Doctor resource starts");
        try {
            chiefDoctorRepository = new ChiefDoctorRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));

        } catch (Exception e) {
            id =-1;
        }
        LOGGER.info("Initialising Chief Doctor resource ends");
    }


    @Override
    public ChiefDoctorRepresentation getChiefDoctor() throws NotFoundException {
        LOGGER.info("Retrieve Chief Doctor");

        ResourceUtils.checkRole(this, Shield.chiefDoctor);

        ChiefDoctorRepository chiefDoctorRepository = new ChiefDoctorRepository(JpaUtil.getEntityManager());
        ChiefDoctor chiefDoctor;

        try {
            Optional<ChiefDoctor> ochief = chiefDoctorRepository.findById(id);
            setExisting(ochief.isPresent());
            if (!isExisting()) {
                LOGGER.config("Chief Doctor id does not exist:" + id);
                throw new NotFoundException("No Chief Doctor with  : " + id);
            } else {
                chiefDoctor = ochief.get();
                LOGGER.finer("Chief Doctor was retrieved");
                ChiefDoctorRepresentation result =
                        new ChiefDoctorRepresentation(chiefDoctor);
                LOGGER.finer("Chief Doctor's data were successfully retrieved");
                return result;
            }
        } catch (Exception e) {
            throw new ResourceException(e);
        }
    }

    @Override
    public ChiefDoctorRepresentation store(ChiefDoctorRepresentation chiefReprIn) throws NotFoundException, BadEntityException {
        LOGGER.finer("Update a Chief Doctor.");

        ResourceUtils.checkRole(this, Shield.chiefDoctor);
        LOGGER.finer("User allowed to update a product.");

        ResourceValidator.notNull(chiefReprIn);
        ResourceValidator.validateChiefDoctor(chiefReprIn);
        LOGGER.finer("Company checked");

        try {

            ChiefDoctor chiefDoctorIn = chiefReprIn.createChiefDoctor();
            chiefDoctorIn.setId(id);

            Optional<ChiefDoctor> chiefDoctorOut;

            Optional<ChiefDoctor> oChief = chiefDoctorRepository.findById(id);

            setExisting(oChief.isPresent());

            if (isExisting()) {
                LOGGER.finer("Update Chief Doctor.");

                chiefDoctorOut = chiefDoctorRepository.update(chiefDoctorIn);

                if (!chiefDoctorOut.isPresent()) {
                    LOGGER.finer("Chief Doctor does not exist.");
                    throw new NotFoundException(
                            "Chief Doctor with the following id does not exist: "
                                    + id);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException(
                        "Company with the following id does not exist: " + id);
            }

            LOGGER.finer("Company successfully updated.");
            return new ChiefDoctorRepresentation(chiefDoctorOut.get());

        } catch (Exception ex) {

            throw new ResourceException(ex);
        }
    }
}