package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import com.pfizer.sacchonapi.resource.util.ResourceValidator;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class MediDataResourceImpl extends ServerResource implements MediDataResource {

    public static final Logger LOGGER = Engine.getLogger(MediDataResourceImpl.class);

    private long id;
    private MediDataRepository mediDataRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising medical data resource starts");
        try {
            mediDataRepository = new MediDataRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));

        } catch (Exception e) {
            id =-1;
        }
        LOGGER.info("Initialising medical data resource ends");
    }


    @Override
    public MediDataRepresentation getMediData() throws NotFoundException {
        LOGGER.info("Retrieve medical data");

        ResourceUtils.checkRole(this, Shield.patient);

        MediDataRepository mediDataRepository = new MediDataRepository(JpaUtil.getEntityManager());
        MediData mediData;

        try {

            Optional<MediData> omedidata = mediDataRepository.findById(id);
            setExisting(omedidata.isPresent());
            if (!isExisting()) {
                LOGGER.config("medical data id does not exist:" + id);
                throw new NotFoundException("No medical data with  : " + id);
            } else {
                mediData = omedidata.get();
                LOGGER.finer("User allowed to retrieve a product.");
                MediDataRepresentation result =
                        new MediDataRepresentation(mediData);
                LOGGER.finer("Medical data successfully retrieved");
                return result;
            }
        } catch (Exception e) {
            throw new ResourceException(e);
        }
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

