package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ConsultationRepresentation;
import com.pfizer.sacchonapi.representation.PatientRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import java.util.List;

public interface PatientListResource {

    @Post("json")
    public PatientRepresentation add(PatientRepresentation companyReprIn) throws BadEntityException;

    @Get("json")
    public List<PatientRepresentation> getPatients() throws NotFoundException;

}
