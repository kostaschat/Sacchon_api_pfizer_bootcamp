package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.DoctorRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;


import java.util.List;

public interface DoctorListResource {
    @Post("json")
    public DoctorRepresentation add(DoctorRepresentation doctorReprIn)
            throws BadEntityException;
    @Get("json")
    public List<DoctorRepresentation> getDoctors() throws NotFoundException;



}

