package com.pfizer.sacchonapi.resource.ChiefDoctor;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ApplicationUserRepresentation;
import com.pfizer.sacchonapi.representation.ConsultationRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface ChiefDoctorListResource {
    @Get("json")
    public List<ApplicationUserRepresentation> getDoctors() throws NotFoundException;
}
