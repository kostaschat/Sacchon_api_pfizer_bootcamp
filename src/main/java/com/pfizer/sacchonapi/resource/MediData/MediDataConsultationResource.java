package com.pfizer.sacchonapi.resource.MediData;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface MediDataConsultationResource {

    @Get("json")
    List<MediDataRepresentation> getMediData() throws NotFoundException;

}
