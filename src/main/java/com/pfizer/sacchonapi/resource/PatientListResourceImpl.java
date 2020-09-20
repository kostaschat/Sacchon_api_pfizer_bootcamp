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
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientListResourceImpl extends ServerResource implements PatientListResource {

    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);

    private PatientRepository patientRepository;
    private EntityManager em;

    @Override
    protected void doRelease(){
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patients resource starts");
        try {
            em = JpaUtil.getEntityManager();
            patientRepository = new PatientRepository (em) ;
        }
        catch(Exception e)
        {
            throw new ResourceException(e);
        }
        LOGGER.info("Initialising patient resource ends");
    }


    @Override
    public PatientRepresentation add(PatientRepresentation patientIn) throws BadEntityException {

            LOGGER.finer("Add patient data.");

            ResourceUtils.checkRole(this, Shield.patient);
            LOGGER.finer("User was allowed to add data.");

            ResourceValidator.notNull(patientIn);
            ResourceValidator.validatePatient(patientIn);
            LOGGER.finer("Patient was checked");

            try {
                Patient patient = patientIn.createPatient();

                Optional<Patient> patientOptOut = patientRepository.save(patient);

                Patient patientOut;
                if (patientOptOut.isPresent())
                    patientOut = patientOptOut.get();
                else
                    throw new
                            BadEntityException(" Patient has not been created");

                PatientRepresentation result = new PatientRepresentation(patientOut);

                LOGGER.finer("Patient successfully added.");

                return result;
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Error when adding a new patient", ex);

                throw new ResourceException(ex);
            }
        }


    public List<PatientRepresentation> getPatients() throws NotFoundException {

        LOGGER.finer("Select all patients.");

        ResourceUtils.checkRoles(this, Shield.doctor, Shield.chiefDoctor);

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
