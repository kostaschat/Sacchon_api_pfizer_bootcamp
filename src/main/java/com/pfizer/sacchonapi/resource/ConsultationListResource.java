package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ConsultationRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface ConsultationListResource {
    @Post("json")
    public ConsultationRepresentation add(ConsultationRepresentation companyReprIn) throws BadEntityException;

    @Get("json")
    public List<ConsultationRepresentation> getConsultations() throws NotFoundException;
}
