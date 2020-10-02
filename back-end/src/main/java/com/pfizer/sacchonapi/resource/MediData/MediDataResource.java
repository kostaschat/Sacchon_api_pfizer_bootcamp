package com.pfizer.sacchonapi.resource.MediData;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.GeneralRepresentation;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface MediDataResource {

    @Get("json")
    MediDataRepresentation getMediData() throws NotFoundException;

    @Put("json")
    MediDataRepresentation store(MediDataRepresentation mediDataReprIn) throws NotFoundException, BadEntityException;

    @Delete("json")
    GeneralRepresentation remove(MediDataRepresentation mediDataReprIn) throws Exception;

}
