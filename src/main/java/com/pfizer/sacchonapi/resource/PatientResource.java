package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.NotFoundException;
import com.pfizer.sacchonapi.representation.PatientRepresentation;
import org.restlet.resource.Get;

public interface PatientResource {

  @Get("json")
  public PatientRepresentation getPatient() throws NotFoundException;

}
