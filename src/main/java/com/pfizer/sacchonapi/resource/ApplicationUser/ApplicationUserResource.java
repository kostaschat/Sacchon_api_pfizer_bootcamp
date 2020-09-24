package com.pfizer.sacchonapi.resource.ApplicationUser;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.ApplicationUser;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.representation.ConsultationRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import java.util.List;

public interface ApplicationUserResource {

    @Put("json")
    public ApplicationUserRepresentation consultPatient() throws NotFoundException, BadEntityException;

    @Delete("json")
    public ApplicationUserRepresentation remove() throws NotFoundException, BadEntityException;
}
