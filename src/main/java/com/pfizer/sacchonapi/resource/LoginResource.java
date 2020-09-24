package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface LoginResource {
    @Get("json")
    public boolean findUser(ApplicationUserRepresentation applicationUserRepresentation) throws BadEntityException;
}
