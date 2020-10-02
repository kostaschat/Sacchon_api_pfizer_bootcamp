package com.pfizer.sacchonapi.resource.Login;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public interface LoginResource {
    @Post("json")
    String findUser(ApplicationUserRepresentation applicationUserRepresentation) throws BadEntityException;

    @Put("json")
    boolean updateUser(ApplicationUserRepresentation applicationUserRepresentation)  throws NotFoundException, BadEntityException;
}
