package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.PatientRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientListResource {

    @Get("json")
    public List<PatientRepresentation> getPatients() throws NotFoundException;

}
