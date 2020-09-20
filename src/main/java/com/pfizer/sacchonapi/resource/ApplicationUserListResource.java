package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface ApplicationUserListResource {

    @Post("json")
    public ApplicationUserRepresentation add(ApplicationUserRepresentation userIn) throws BadEntityException;

    @Get("json")
    public List<ApplicationUserRepresentation> getUsers() throws NotFoundException;
}
