package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import org.restlet.resource.Get;

public interface MediDataResource {

    @Get("json")
    public MediDataRepresentation getMediData() throws NotFoundException;
}