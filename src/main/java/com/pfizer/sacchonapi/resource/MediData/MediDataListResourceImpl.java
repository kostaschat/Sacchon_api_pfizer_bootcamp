package com.pfizer.sacchonapi.resource.MediData;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.*;
import com.pfizer.sacchonapi.repository.ApplicationUserRepository;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Role;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.Request;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MediDataListResourceImpl extends ServerResource implements MediDataListResource {

    public static final Logger LOGGER = Engine.getLogger(MediDataResourceImpl.class);
    private MediDataRepository mediDataRepository;
    private ApplicationUserRepository applicationUserRepository;
    private PatientRepository patientRepository;


    private String fromDate;
    private String toDate;
    private long pid;
    private EntityManager em;

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising medidata resource starts");
        try {
            em = JpaUtil.getEntityManager();
            applicationUserRepository = new ApplicationUserRepository(em);
            mediDataRepository = new MediDataRepository(em);
            patientRepository = new PatientRepository(em);

            try {
                fromDate = getAttribute("fromdate");
                toDate = getAttribute("todate");

            } catch (Exception e) {
                fromDate = null;
                toDate = null;
                System.out.println("One of the dates was null");
                LOGGER.info(e.getMessage());
            }

            pid = Long.parseLong(getAttribute("pid"));
        } catch (Exception e) {
            pid = -1;
            LOGGER.info(e.getMessage());
        }


        LOGGER.info("Initialising medidata resource ends");
    }

    public List<MediDataRepresentation> getMediDatas() throws NotFoundException {

        LOGGER.finer("Select all medical data.");

        ResourceUtils.checkRoles(this, Shield.patient, Shield.doctor, Shield.chiefDoctor);

        Request request = Request.getCurrent();
        String username = request.getClientInfo().getUser().getName();
        Optional<ApplicationUser> user = applicationUserRepository.findByUsername(username);

        try {
            List<MediData> mediData;
            List<MediDataRepresentation> result = new ArrayList<>();
            if (pid != -1) {

                Doctor doctorOut;
                if (user.isPresent()) {
                    doctorOut = user.get().getDoctor();
                } else {
                    LOGGER.config("This doctor cannot be found in the database:" + username);
                    throw new NotFoundException("No doctor with name : " + username);
                }

                long d_id = doctorOut.getId();

                mediData = mediDataRepository.findMediDataWithNoConsultation(pid, d_id);
            } else {
                Role role;

                if (user.isPresent()) {
                    role = user.get().getRole();
                } else {
                    LOGGER.config("This doctor cannon be found in the database:" + username);
                    throw new NotFoundException("No doctor with name: " + username);
                }
                if (role.getRoleName().equals("chiefDoctor")) {
                    mediData = getMonitoringMediData();
                } else {

                    Patient patientOut;
                    if (user.isPresent()) {
                        patientOut = user.get().getPatient();
                    } else {
                        LOGGER.config("This Patient cannon be found in the database:" + username);
                        throw new NotFoundException("No patient with name : " + username);
                    }
                    long id = patientOut.getId();
                    mediData = mediDataRepository.findMediDataWithNoConsultation(id);
                }
            }
            mediData.forEach(m -> result.add(new MediDataRepresentation(m)));
            return result;
        } catch (Exception e) {
            throw new NotFoundException("consultations not found");
        }
    }

    public List<MediData> getMonitoringMediData() {
        return mediDataRepository.findMonitoredMediData(pid, fromDate, toDate);
    }

    @Override
    public MediDataRepresentation add(MediDataRepresentation mediDataIn) throws BadEntityException {

        LOGGER.finer("Add a new medical data.");

        ResourceUtils.checkRole(this, Shield.patient);
        LOGGER.finer("User allowed to add medical data.");


        ResourceValidator.notNull(mediDataIn);
        ResourceValidator.validate(mediDataIn);
        LOGGER.finer("Medical Data checked");

        Request request = Request.getCurrent();
        String username = request.getClientInfo().getUser().getName();
        Optional<ApplicationUser> user = applicationUserRepository.findByUsername(username);



        try {
            if (user.isPresent() && user.get().getPatient().isConsultationPending()) {
                LOGGER.finer("Please wait a doctor to consult your previous Medical Data");
                return null;
            }else {
                MediData mediData = mediDataIn.createMediData();
                Optional<MediData> mediDataOptOut = mediDataRepository.save(mediData);

                MediData mediDataOut;
                if (mediDataOptOut.isPresent()) {
                    mediDataOut = mediDataOptOut.get();

                    Patient patientOut;
                    if (user.isPresent()) {
                        patientOut = user.get().getPatient();
                    } else {
                        LOGGER.config("This patient cannot be found in the database:" + username);
                        throw new NotFoundException("No Patient with name : " + username);
                    }

                    mediData.setPatient(patientOut);
                    mediDataRepository.save(mediData);
                    boolean checkIfPassed = mediDataRepository.checkIfReady(patientOut.getId());
                    if (checkIfPassed) {
                        patientOut.setConsultationPending(true);
                        patientOut.setHasConsultation(false);
                        patientRepository.save(patientOut);
                    }

                } else
                    throw new BadEntityException(" Medical Data has not been created");

                MediDataRepresentation result = new MediDataRepresentation(mediDataOut);

                LOGGER.finer("Product successfully added.");

                return result;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a medical data", ex);
            throw new ResourceException(ex);
        }
    }
}
