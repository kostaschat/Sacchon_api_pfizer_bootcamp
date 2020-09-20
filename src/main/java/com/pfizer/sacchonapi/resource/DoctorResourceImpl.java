package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.Doctor;
import com.pfizer.sacchonapi.repository.DoctorRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.DoctorRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class DoctorResourceImpl  extends ServerResource implements DoctorResource {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);

    private long id;
    private DoctorRepository doctorRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor data resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));

        } catch (Exception e) {
            id =-1;
        }
        LOGGER.info("Initialising doctor data resource ends");
    }


    @Override
    public DoctorRepresentation getDoctor() throws NotFoundException {
        LOGGER.info("Retrieve doctor data");
        //check authorization
        ResourceUtils.checkRoles(this,Shield.doctor, Shield.chiefDoctor);

        // Initialize the persistence layer.
        DoctorRepository doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        Doctor doctor;

        try {

            Optional<Doctor> odcoctor = doctorRepository.findById(id);
            setExisting(odcoctor.isPresent());
            if (!isExisting()) {
                LOGGER.config("doctor data id does not exist:" + id);
                throw new NotFoundException("No doctor data with  : " + id);
            } else {
                doctor = odcoctor.get();
                LOGGER.finer("User allowed to retrieve a data.");
                DoctorRepresentation result =
                        new DoctorRepresentation(doctor);
                LOGGER.finer("Doctor data successfully retrieved");
                return result;
            }
        } catch (Exception e) {
            throw new ResourceException(e);
        }
    }

    @Override
    public DoctorRepresentation store(DoctorRepresentation doctorReprIn) throws NotFoundException, BadEntityException {
        LOGGER.finer("Update a doctor.");

        ResourceUtils.checkRoles(this, Shield.doctor, Shield.chiefDoctor);
        LOGGER.finer("doctor allowed to update a data.");

        ResourceValidator.notNull(doctorReprIn);
        ResourceValidator.validate(doctorReprIn);
        LOGGER.finer("doctor checked");

        try {

            Doctor doctorIn = doctorReprIn.createDoctor();
            doctorIn.setId(id);

            Optional<Doctor> doctorOut;

            Optional<Doctor> oDoctor = doctorRepository.findById(id);

            setExisting(oDoctor.isPresent());

            if (isExisting() ) {
                LOGGER.finer("Update doctor.");

                doctorOut = doctorRepository.update(doctorIn);

                if (!doctorOut.isPresent()) {
                    LOGGER.finer("Doctor does not exist.");
                    throw new NotFoundException(
                            "Doctor with the following id does not exist: "
                                    + id);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException(
                        "Doctor with the following id does not exist: " + id);
            }

            LOGGER.finer("Company successfully updated.");
            return new DoctorRepresentation(doctorOut.get());

        } catch (Exception ex) {

            throw new ResourceException(ex);
        }
    }
}

