package com.pfizer.sacchonapi.resource.Patient;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientUserListResource {

    @Get("json")
    public List<ApplicationUserRepresentation> getUsers() throws NotFoundException;

}
