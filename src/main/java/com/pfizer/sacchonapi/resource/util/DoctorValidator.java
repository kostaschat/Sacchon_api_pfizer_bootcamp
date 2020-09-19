package com.pfizer.sacchonapi.resource.util;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.representation.DoctorRepresentation;


public class DoctorValidator {
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
     * @param doctorRepresentation
     * @throws BadEntityException
     */
    public static void validate(DoctorRepresentation doctorRepresentation)
            throws BadEntityException {
        if ( doctorRepresentation.isActive()==false) {
            throw new BadEntityException(
                    "doctor  cannot be null");
        }
    }
}
