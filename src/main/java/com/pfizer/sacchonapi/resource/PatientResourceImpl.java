package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.ConsultationRepresentation;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import com.pfizer.sacchonapi.representation.PatientRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.data.Product;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class PatientResourceImpl extends ServerResource implements PatientResource {

    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);

    private long id;
    private PatientRepository patientRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising medical data resource starts");
        try {
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));

        } catch (Exception e) {
            id =-1;
        }
        LOGGER.info("Initialising medical data resource ends");
    }


    @Override
    public PatientRepresentation store(PatientRepresentation patientRepr) throws NotFoundException, BadEntityException {

        LOGGER.finer("Update a patient.");

        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("Patient was allowed to update his role.");

        // Check given entity
        ResourceValidator.notNull(patientRepr);
        ResourceValidator.validatePatient(patientRepr);
        LOGGER.finer("Company checked");

        try {

            // Convert PatientRepresentation to Patient
            Patient patientIn = patientRepr.createPatient();
            patientIn.setId(id);

            Optional<Patient> patientOut;

            Optional<Patient> oPatient = patientRepository.findById(id);

            setExisting(oPatient.isPresent());

            // If product exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update patient.");

                // Update product in DB and retrieve the new one.
                patientOut = patientRepository.save(patientIn);


                // Check if retrieved patient is not null : if it is null it
                // means that the id is wrong.
                if (!patientOut.isPresent()) {
                    LOGGER.finer("Patient does not exist.");
                    throw new NotFoundException(
                            "Patient with the following id does not exist: "
                                    + id);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException(
                        "Patient with the following id does not exist: " + id);
            }

            LOGGER.finer("Patient successfully updated.");
            return new PatientRepresentation(patientOut.get());

        } catch (Exception ex) {

            throw new ResourceException(ex);
        }
    }

    @Override
    public PatientRepresentation getPatient() throws NotFoundException {
        LOGGER.info("Retrieve patient's data");
        //check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);

        // Initialize the persistence layer.
        PatientRepository mediDataRepository = new PatientRepository(JpaUtil.getEntityManager());
        Patient patient;

        try {

            Optional<Patient> opatient = patientRepository.findById(id);
            setExisting(opatient.isPresent());
            if (!isExisting()) {
                LOGGER.config("patient id does not exist:" + id);
                throw new NotFoundException("No patient with  : " + id);
            } else {
                patient = opatient.get();
                LOGGER.finer("A patient was retrieved");
                PatientRepresentation result =
                        new PatientRepresentation(patient);
                LOGGER.finer("Patient's data were successfully retrieved");
                return result;
            }
        } catch (Exception e) {
            throw new ResourceException(e);
        }


    }

}
