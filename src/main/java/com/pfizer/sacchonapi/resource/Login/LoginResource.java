package com.pfizer.sacchonapi.resource.Login;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface LoginResource {
    @Post("json")
    public boolean findUser(ApplicationUserRepresentation applicationUserRepresentation) throws BadEntityException;
}
