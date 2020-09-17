package com.pfizer.sacchonapi.resource.util;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.representation.MediDataRepresentation;
import com.pfizer.sacchonapi.representation.PatientRepresentation;

public class ResourceValidator {
    /**
     * Checks that the given entity is not null.
     *
     * @param entity
     *            The entity to check.
     * @throws BadEntityException
     *             In case the entity is null.
     */
    public static void notNull(Object entity) throws BadEntityException {
        if (entity == null) {
            throw new BadEntityException("No input entity");
        }
    }

    /**
     * Checks that the given company is valid.
     *
     * @param mediDataRepresentation
     * @throws BadEntityException
     */
    public static void validate(MediDataRepresentation mediDataRepresentation)
            throws BadEntityException {
        if ( mediDataRepresentation.getCarb()==0) {
            throw new BadEntityException(
                    "product name cannot be null");
        }
    }

    public static void validatePatient(PatientRepresentation patientRepresentation)
            throws BadEntityException {
        if ( patientRepresentation.getFirstName() == null || patientRepresentation.getLastName() == null || patientRepresentation.getPassword() == null || patientRepresentation.getEmail() == null ) {
            throw new BadEntityException(
                    "The required fields cannot not be empty");
        }
    }



}
