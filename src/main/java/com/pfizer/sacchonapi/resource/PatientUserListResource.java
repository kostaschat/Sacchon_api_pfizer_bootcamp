package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.NotFoundException;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientUserListResource {

    @Get("json")
    public <T> List<T> getUsers() throws NotFoundException;

}
