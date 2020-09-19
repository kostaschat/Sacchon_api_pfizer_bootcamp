package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.model.Consultation;
import com.pfizer.sacchonapi.representation.ConsultationRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface ConsultationResource {
    @Get("json")
    public ConsultationRepresentation getConsultation() throws NotFoundException;

    @Put("json")
    public ConsultationRepresentation store(ConsultationRepresentation consultationReprIn) throws NotFoundException, BadEntityException;
}
