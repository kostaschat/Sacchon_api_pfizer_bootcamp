package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.Doctor;
import com.pfizer.sacchonapi.repository.DoctorRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.DoctorRepresentation;
import com.pfizer.sacchonapi.resource.util.DoctorValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.data.Status;
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
            doctorRepository =
                    new DoctorRepository(JpaUtil.getEntityManager()) ;

        }
        catch(Exception e)
        {
            // needs to put a print msg
        }

        LOGGER.info("Initialising doctor resource ends");
    }




    public List<DoctorRepresentation> getDoctors() throws NotFoundException {

        LOGGER.finer("Select all doctors datas.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);                //d i need to change the role to chief??

        try {

            List<Doctor> doctors =
                    doctorRepository.findAll();
            List<DoctorRepresentation> result =
                    new ArrayList<>();



            doctors.forEach(doctor ->
                    result.add(new DoctorRepresentation(doctor)));


            return result;
        } catch (Exception e) {
            throw new NotFoundException("doctor not found");
        }
    }

    @Override
    public DoctorRepresentation add(DoctorRepresentation doctorRepresentationIn) throws BadEntityException {

        LOGGER.finer("Add a new doctor data.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        LOGGER.finer("User allowed to add doctor data.");

        // Check entity
        DoctorValidator.notNull(doctorRepresentationIn);
        DoctorValidator.validate(doctorRepresentationIn);
        LOGGER.finer("doctor checked");

        try {
            Doctor doctorIn = new Doctor();
            doctorIn.setPhoneNumber(doctorRepresentationIn.getPhoneNumber());
            doctorIn.setUsername(doctorRepresentationIn.getUsername());
            doctorIn.setLastName(doctorRepresentationIn.getLastName());
            doctorIn.setFirstName(doctorRepresentationIn.getFirstName());
            doctorIn.setEmail(doctorRepresentationIn.getEmail());
            doctorIn.setPassword(doctorRepresentationIn.getPassword());
            doctorIn.setCity(doctorRepresentationIn.getCity());
            doctorIn.setAddress(doctorRepresentationIn.getAddress());
            doctorIn.setActive(doctorRepresentationIn.isActive());               // do i need to set active??
            doctorIn.setDob(doctorRepresentationIn.getDob());
            doctorIn.setZipCode(doctorRepresentationIn.getZipCode());
            doctorIn.setCreationDate(doctorRepresentationIn.getCreationDate());

            Optional<Doctor> doctorOut =
                    doctorRepository.save(doctorIn);
            Doctor doctor = null;
            if (doctorOut.isPresent())
                doctor = doctorOut.get();
            else
                throw new
                        BadEntityException(" Doctor has not been created");

            DoctorRepresentation result =
                    new DoctorRepresentation();
            result.setAddress(doctor.getAddress());
            result.setCity(doctor.getCity());
            result.setEmail(doctor.getEmail());
            result.setFirstName(doctor.getFirstName());
            result.setLastName(doctor.getLastName());
            result.setUsername(doctor.getUsername());
            result.setZipCode(doctor.getZipCode());
            result.setPassword(doctor.getPassword());
            result.setCreationDate(doctor.getCreationDate());
            result.setDob(doctor.getDob());
            result.setActive(doctor.isActive());                                       // do i need to set active??
            result.setPhoneNumber(doctor.getPhoneNumber());
            getResponse().setLocationRef(
                    "http://localhost:9000/v1/doctor/"+doctor.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.finer("Doctor successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a doctor", ex);
            throw new ResourceException(ex);
        }
    }
}

