package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.NotFoundException;
import org.restlet.resource.Get;

public interface MediDataResource {

    @Get("json")
    public MediDataResource getMediData() throws NotFoundException;
}
