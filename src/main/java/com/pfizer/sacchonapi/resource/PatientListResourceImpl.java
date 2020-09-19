package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.PatientRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
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

public class PatientListResourceImpl extends ServerResource implements PatientListResource {

    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);

    private PatientRepository patientRepository;


    @Override
    protected void doInit() {
        LOGGER.info("Initialising patients resource starts");
        try {
            patientRepository =
                    new PatientRepository (JpaUtil.getEntityManager()) ;

        }
        catch(Exception e)
        {
            throw new ResourceException(e);
        }
        LOGGER.info("Initialising patient resource ends");
    }


    @Override
    public PatientRepresentation add(PatientRepresentation patientRepresentation) throws BadEntityException {


            LOGGER.finer("Add a new product.");

            // Check authorization
            ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
            LOGGER.finer("Doctor was allowed to add a product.");

            // Check entity

            ResourceValidator.notNull(patientRepresentation);
            ResourceValidator.validatePatient(patientRepresentation);
            LOGGER.finer("Patient was checked");

            try {

                // Convert CompanyRepresentation to Company
                Patient patientIn = new Patient();
                patientIn.setFirstName(patientRepresentation.getFirstName());
                patientIn.setLastName(patientRepresentation.getLastName());
                patientIn.setUsername(
                        patientRepresentation.getUsername());

                patientIn.setEmail(patientRepresentation.getEmail());
                patientIn.setPassword(patientRepresentation.getPassword());
                patientIn.setAddress(patientRepresentation.getAddress());
                patientIn.setCity(patientRepresentation.getCity());
                patientIn.setZipCode(patientRepresentation.getZipCode());
                patientIn.setPhoneNumber(patientRepresentation.getPhoneNumber());
                patientIn.setDob(patientRepresentation.getDob());
                patientIn.setCreationDate(patientRepresentation.getCreationDate());

                patientIn.setActive(patientRepresentation.isActive());
                patientIn.setHasConsultation(patientRepresentation.isHasConsultation());
                patientIn.setConsultationPending(patientRepresentation.isConsultationPending());
                patientIn.setHasDoctor(patientRepresentation.isHasDoctor());


                Optional<Patient> patientOut =
                        patientRepository.save(patientIn);
                Patient pa = null;
                if (patientOut.isPresent())
                    pa = patientOut.get();
                else
                    throw new
                            BadEntityException(" Patient has not been created");

                PatientRepresentation result =
                        new PatientRepresentation();
                result.setFirstName(pa.getFirstName());
                result.setLastName(pa.getLastName());
                result.setUsername(pa.getUsername());
                result.setEmail(pa.getEmail());
                result.setPassword(pa.getEmail());
                result.setAddress(pa.getAddress());
                result.setCity(pa.getCity());
                result.setZipCode(pa.getZipCode());
                result.setPhoneNumber(pa.getPhoneNumber());
                result.setDob(pa.getDob());
                result.setCreationDate(pa.getCreationDate());
                result.setActive(pa.isActive());
                result.setHasConsultation(pa.isHasConsultation());
                result.setConsultationPending(pa.isConsultationPending());
                result.setHasDoctor(pa.isHasDoctor());

                result.setUri("http://localhost:9000/v1/patient/"+ pa.getId());

                getResponse().setLocationRef(
                        "http://localhost:9000/v1/patient/"+ pa.getId());

                getResponse().setStatus(Status.SUCCESS_CREATED);

                LOGGER.finer("Patient successfully added.");

                return result;
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Error when adding a new patient", ex);

                throw new ResourceException(ex);
            }


        }


    public List<PatientRepresentation> getPatients() throws NotFoundException {

        LOGGER.finer("Select all patients.");

        ResourceUtils.checkRoles(this, Shield.ROLE_DOCTOR, Shield.ROLE_CHIEF_DOCTOR);

        try{
            List<Patient> patients = patientRepository.findAll();
            List<PatientRepresentation> result = new ArrayList<>();

            patients.forEach(patient -> result.add (new PatientRepresentation(patient)));

            return result;
        }
        catch(Exception e)
        {
            throw new NotFoundException("patients could not be found");
        }
    }
}
