package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import com.pfizer.sacchonapi.representation.PatientRepresentation;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
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
    public PatientRepresentation getPatient() throws NotFoundException {
        LOGGER.info("Retrieve medical data");
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
