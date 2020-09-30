package com.pfizer.sacchonapi.resource.ApplicationUser;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface ApplicationUserListResource {

    @Post("json")
    ApplicationUserRepresentation add(ApplicationUserRepresentation userIn) throws BadEntityException;

    @Get("json")
    List<ApplicationUserRepresentation> getUsers() throws NotFoundException;
}
