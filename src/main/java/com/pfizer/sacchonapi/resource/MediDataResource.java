package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface MediDataResource {

    @Get("json")
    public MediDataRepresentation getMediData() throws NotFoundException;

    @Put("json")
    public MediDataRepresentation store(MediDataRepresentation mediDataReprIn) throws NotFoundException, BadEntityException;

}
