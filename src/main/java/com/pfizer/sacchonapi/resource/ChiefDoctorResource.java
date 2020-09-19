package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ChiefDoctorRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface ChiefDoctorResource {
    @Get("json")
    public ChiefDoctorRepresentation getChiefDoctor() throws NotFoundException;

    @Put("json")
    public ChiefDoctorRepresentation store(ChiefDoctorRepresentation chiefReprIn)
            throws NotFoundException, BadEntityException;
}
