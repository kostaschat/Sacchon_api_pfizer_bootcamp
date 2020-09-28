package com.pfizer.sacchonapi.resource.Patient;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import java.util.List;

public interface PatientListResource {
    @Get("json")
    public List<ApplicationUserRepresentation> getPatients() throws NotFoundException;

    @Put("json")
    public void errorModify() throws NotFoundException, BadEntityException;

}
