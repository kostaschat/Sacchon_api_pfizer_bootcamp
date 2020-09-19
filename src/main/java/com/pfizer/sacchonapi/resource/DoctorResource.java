package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.DoctorRepresentation;

import org.restlet.resource.Get;
import org.restlet.resource.Put;



public interface DoctorResource {
    @Get("json")
    public DoctorRepresentation getDoctor() throws NotFoundException;



    @Put("json")
    public DoctorRepresentation store(DoctorRepresentation doctorReprIn)
            throws NotFoundException, BadEntityException;
   }

