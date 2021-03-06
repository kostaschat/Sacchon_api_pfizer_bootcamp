package com.pfizer.sacchonapi.resource.ApplicationUser;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface ApplicationUserResource {

    @Get("json")
    ApplicationUserRepresentation getUser() throws NotFoundException;

    @Put("json")
    ApplicationUserRepresentation consultPatient() throws NotFoundException, BadEntityException;

    @Delete("json")
    ApplicationUserRepresentation remove() throws NotFoundException, BadEntityException;
}
