package com.pfizer.sacchonapi.resource;

import com.pfizer.sacchonapi.exception.NotFoundException;
import org.restlet.resource.Get;

public interface PatientResource {

  @Get("json")
  public PatientResource getPatient() throws NotFoundException;


}
