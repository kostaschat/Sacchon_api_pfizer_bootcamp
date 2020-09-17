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
import org.restlet.data.Status;
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

    @Override
    protected void doInit() {
        LOGGER.info("Initialising product resource starts");
        try {
            mediDataRepository =
                    new MediDataRepository (JpaUtil.getEntityManager()) ;

        }
        catch(Exception e)
        {

        }

        LOGGER.info("Initialising product resource ends");
    }

    public List<MediDataRepresentation> getMediDatas() throws NotFoundException {

        LOGGER.finer("Select all medical datas.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);

        try {

            List<MediData> mediData =
                    mediDataRepository.findAll();
            List<MediDataRepresentation> result =
                    new ArrayList<>();


//            for (Product product :products)
//                result.add (new ProductRepresentation(product));


            mediData.forEach(product ->
                    result.add(new MediDataRepresentation(product)));


            return result;
        } catch (Exception e) {
            throw new NotFoundException("products not found");
        }
    }

        @Override
        public MediDataRepresentation add
        (MediDataRepresentation mediDataRepresentationIn)
            throws BadEntityException {

            LOGGER.finer("Add a new medical data.");

            // Check authorization
            ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
            LOGGER.finer("User allowed to add medical data.");

            // Check entity

            ResourceValidator.notNull(mediDataRepresentationIn);
            ResourceValidator.validate(mediDataRepresentationIn);
            LOGGER.finer("Product checked");

            try {

                // Convert CompanyRepresentation to Company
                MediData mediDataIn = new MediData();
                mediDataIn.setGlucose(mediDataRepresentationIn.getGlucose());
                mediDataIn.setCarb(mediDataRepresentationIn.getCarb());
                mediDataIn.setMeasuredDate(
                        mediDataRepresentationIn.getMeasuredDate());


                Optional<MediData> mediDataOut =
                        mediDataRepository.save(mediDataIn);
                MediData mediData = null;
                if (mediDataOut.isPresent())
                    mediData = mediDataOut.get();
                else
                    throw new
                            BadEntityException(" Product has not been created");

                MediDataRepresentation result =
                        new MediDataRepresentation();
                result.setCarb(mediData.getCarb());
                result.setGlucose(mediData.getGlucose());
                result.setMeasuredDate(mediData.getMeasuredDate());
                result.setUri("http://localhost:9000/v1/product/"+mediData.getId());

                getResponse().setLocationRef(
                        "http://localhost:9000/v1/product/"+mediData.getId());
                getResponse().setStatus(Status.SUCCESS_CREATED);

                LOGGER.finer("Product successfully added.");

                return result;
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Error when adding a product", ex);

                throw new ResourceException(ex);
            }
    }
}
