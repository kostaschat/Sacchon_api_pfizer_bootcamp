package com.pfizer.sacchonapi.resource.MediData;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;


public interface MediDataListResource {

    @Post("json")
    MediDataRepresentation add(MediDataRepresentation companyReprIn) throws BadEntityException;

    @Get("json")
    List<MediDataRepresentation> getMediDatas() throws NotFoundException;

}
