package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.Request;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class MediDataResourceImpl extends ServerResource implements MediDataResource {

    public static final Logger LOGGER = Engine.getLogger(MediDataResourceImpl.class);


    private ApplicationUserRepository userRepository;
    private MediDataRepository mediDataRepository;
    private PatientRepository patientRepository;
    private EntityManager em;

    private long id;
    private String fromDate;
    private String toDate;
    private String dataType;

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising medical data resource starts");
        try {
            em = JpaUtil.getEntityManager();
            mediDataRepository = new MediDataRepository(em);

            userRepository = new ApplicationUserRepository(em);
            mediDataRepository = new MediDataRepository(em);
            patientRepository = new PatientRepository(em);

            try {
                fromDate = getAttribute("fromdate");
                toDate = getAttribute("todate");
                dataType = getAttribute("datatype");

            } catch (Exception e) {
                fromDate = null;
                toDate = null;
                dataType = null;
                System.out.println("Something is null");
                LOGGER.info(e.getMessage());
            }

            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            id = -1;
            LOGGER.info(e.getMessage());
        }

        LOGGER.info("Initialising medical data resource ends");
    }


    @Override
    public MediDataRepresentation getMediData() throws NotFoundException {
        LOGGER.info("Retrieve medical data");

        ResourceUtils.checkRoles(this, Shield.patient, Shield.doctor);

        MediDataRepository mediDataRepository = new MediDataRepository(JpaUtil.getEntityManager());
        MediDataRepresentation result;
        MediData mediData;

        double average_value;

        try {

            //if toDate or datatype is null, fromDate will also get the value null
            if (fromDate != null) {
                //we need the logged in patient's id
                Request request = Request.getCurrent();
                String currentUser = request.getClientInfo().getUser().getName();
                Optional<ApplicationUser> user = userRepository.findByUsername(currentUser);
                Patient patient = null;

                if (user.isPresent()) {
                    patient = user.get().getPatient();
                } else {
                    LOGGER.config("This patient cannon be found in the database:" + currentUser);
                    throw new NotFoundException("No patient with name : " + currentUser);
                }

                System.out.println("Before getting the value");
                System.out.println(fromDate);
                System.out.println(toDate);
                System.out.println(dataType);
                System.out.println(patient.getId());
                average_value = mediDataRepository.average(fromDate, toDate, dataType, patient.getId());
                System.out.println("This is the value" + average_value);
                result = new MediDataRepresentation();
                if (dataType.equals("glucose"))
                    result.setGlucose(average_value);
                else result.setCarb(average_value);

            } else {

                Optional<MediData> omedidata = mediDataRepository.findById(id);
                setExisting(omedidata.isPresent());
                if (!isExisting()) {
                    LOGGER.config("medical data id does not exist:" + id);
                    throw new NotFoundException("No medical data with  : " + id);
                } else {
                    mediData = omedidata.get();
                    LOGGER.finer("User allowed to retrieve a product.");
                    result = new MediDataRepresentation(mediData);
                    LOGGER.finer("Medical data successfully retrieved");
                }
            }
        } catch (Exception e) {
            throw new ResourceException(e);
        }

        return result;
    }

    @Override
    public MediDataRepresentation store(MediDataRepresentation mediDataReprIn) throws NotFoundException, BadEntityException {
        LOGGER.finer("Update a product.");

        ResourceUtils.checkRole(this, Shield.patient);
        LOGGER.finer("User allowed to update medical data.");

        ResourceValidator.notNull(mediDataReprIn);
        ResourceValidator.validate(mediDataReprIn);
        LOGGER.finer("Company checked");

        try {
            MediData mediDataIn = mediDataReprIn.createMediData();
            mediDataIn.setId(id);

            Optional<MediData> mediDataOut;

            Optional<MediData> oMediData = mediDataRepository.findById(id);

            setExisting(oMediData.isPresent());

            if (isExisting()) {
                LOGGER.finer("Update product.");

                mediDataOut = mediDataRepository.update(mediDataIn);

                if (!mediDataOut.isPresent()) {
                    LOGGER.finer("Medical data does not exist.");
                    throw new NotFoundException("Medical Data with the following id does not exist: " + id);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException("Company with the following id does not exist: " + id);
            }

            LOGGER.finer("Company successfully updated.");
            return new MediDataRepresentation(mediDataOut.get());

        } catch (Exception ex) {

            throw new ResourceException(ex);
        }
    }
}

