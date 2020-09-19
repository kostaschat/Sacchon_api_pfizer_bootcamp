package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.PatientRepresentation;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
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

        }

        LOGGER.info("Initialising patient resource ends");
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
