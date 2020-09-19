package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.ConsultationRepresentation;
import com.pfizer.sacchonapi.representation.PatientRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public interface PatientResource {


  @Put("json")
  public PatientRepresentation store(PatientRepresentation patientRep)
          throws NotFoundException, BadEntityException;

  @Get("json")
  public PatientRepresentation getPatient() throws NotFoundException;


}
