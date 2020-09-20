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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorListResourceImpl extends ServerResource implements DoctorListResource{

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private DoctorRepository doctorRepository ;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager()) ;
        }
        catch(Exception e)
        {
            throw new ResourceException(e);
        }

        LOGGER.info("Initialising doctor resource ends");
    }

    public List<DoctorRepresentation> getDoctors() throws NotFoundException {

        LOGGER.finer("Select all doctors datas.");

        ResourceUtils.checkRole(this, Shield.chiefDoctor);

        try {
            List<Doctor> doctors = doctorRepository.findAll();
            List<DoctorRepresentation> result = new ArrayList<>();

            doctors.forEach(doctor ->
                    result.add(new DoctorRepresentation(doctor)));

            return result;
        } catch (Exception e) {
            throw new NotFoundException("doctor not found");
        }
    }

    @Override
    public DoctorRepresentation add(DoctorRepresentation doctorIn) throws BadEntityException {

        LOGGER.finer("Add a new doctor data.");

        ResourceUtils.checkRole(this, Shield.doctor);
        LOGGER.finer("User allowed to add doctor data.");

        // Check entity
        ResourceValidator.notNull(doctorIn);
        ResourceValidator.validate(doctorIn);
        LOGGER.finer("doctor checked");

        try {
            Doctor doctor = doctorIn.createDoctor();

            Optional<Doctor> doctorOptOut = doctorRepository.save(doctor);

            Doctor doctorOut;
            if (doctorOptOut.isPresent())
                doctorOut = doctorOptOut.get();
            else
                throw new
                        BadEntityException("Doctor has not been created");

            DoctorRepresentation result = new DoctorRepresentation(doctorOut);

            LOGGER.finer("Doctor successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a doctor", ex);
            throw new ResourceException(ex);
        }
    }
}