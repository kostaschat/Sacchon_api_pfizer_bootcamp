package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import com.pfizer.sacchonapi.security.ResourceUtils;
import com.pfizer.sacchonapi.security.Shield;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;

public class MediDataResourceImpl extends ServerResource implements MediDataResource {

    private long id;
    private MediDataRepository mediDataRepository;


    protected void doInit() {

        try {
            mediDataRepository = new MediDataRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));

        } catch (Exception e) {
            id = -1;
        }
    }


    @Override
    public MediDataRepresentation getMediData() throws NotFoundException {

        //check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);

        // Initialize the persistence layer.
        MediDataRepository mediDataRepository = new MediDataRepository(JpaUtil.getEntityManager());
        MediData mediData;

        try {

            Optional<MediData> medidata = mediDataRepository.findById(id);
            setExisting(medidata.isPresent());
            if (!isExisting()) {

                throw new NotFoundException("No medidata with  : " + id);
            } else {
                mediData = medidata.get();
                MediDataRepresentation result =
                        new MediDataRepresentation(mediData);
                return result;
            }
        } catch (Exception e) {
            throw new ResourceException(e);
        }


    }

}

