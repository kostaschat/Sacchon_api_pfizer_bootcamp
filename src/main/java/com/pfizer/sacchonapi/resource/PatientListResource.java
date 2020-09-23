package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientListResource {
    @Get("json")
    public List<ApplicationUserRepresentation> getPatients() throws NotFoundException;
}
