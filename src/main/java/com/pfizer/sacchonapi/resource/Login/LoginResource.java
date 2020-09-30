package com.pfizer.sacchonapi.resource.Login;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import org.restlet.resource.Post;

public interface LoginResource {
    @Post("json")
    String findUser(ApplicationUserRepresentation applicationUserRepresentation) throws BadEntityException;
}
