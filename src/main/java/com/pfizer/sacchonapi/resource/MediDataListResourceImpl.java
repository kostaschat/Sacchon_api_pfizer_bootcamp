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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MediDataListResourceImpl  extends ServerResource implements MediDataListResource{

    public static final Logger LOGGER = Engine.getLogger(MediDataResourceImpl.class);
    private MediDataRepository mediDataRepository ;

    private long id;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising medidata resource starts");
        try {
            mediDataRepository = new MediDataRepository (JpaUtil.getEntityManager()) ;
            id = Long.parseLong(getAttribute("id"));
        }
        catch(Exception e)
        {
            id =-1;
            LOGGER.info(e.getMessage());
        }

        LOGGER.info("Initialising medidata resource ends");
    }

    public List<MediDataRepresentation> getMediDatas() throws NotFoundException {

        LOGGER.finer("Select all medical datas.");

        ResourceUtils.checkRole(this, Shield.patient);

        try {
            List<MediData> mediData = mediDataRepository.findAll();
            List<MediDataRepresentation> result = new ArrayList<>();

            mediData.forEach(product ->
                    result.add(new MediDataRepresentation(product)));

            return result;
        } catch (Exception e) {
            throw new NotFoundException("products not found");
        }
    }

        @Override
        public MediDataRepresentation add(MediDataRepresentation mediDataIn) throws BadEntityException {

            LOGGER.finer("Add a new medical data.");

            ResourceUtils.checkRole(this, Shield.patient);
            LOGGER.finer("User allowed to add medical data.");


            ResourceValidator.notNull(mediDataIn);
            ResourceValidator.validate(mediDataIn);
            LOGGER.finer("Medical Data checked");

            try {
                MediData mediData = mediDataIn.createMediData();

                Optional<MediData> mediDataOptOut = mediDataRepository.save(mediData);

                MediData mediDataOut;
                if (mediDataOptOut.isPresent())
                    mediDataOut = mediDataOptOut.get();
                else
                    throw new BadEntityException(" Medical Data has not been created");

                MediDataRepresentation result = new MediDataRepresentation(mediDataOut);

                LOGGER.finer("Product successfully added.");

                return result;
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Error when adding a medical data", ex);

                throw new ResourceException(ex);
            }
    }
}
