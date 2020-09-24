package com.pfizer.sacchonapi.resource.util;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.representation.*;

public class ResourceValidator {
    /**
     * Checks that the given entity is not null.
     *
     * @param entity The entity to check.
     * @throws BadEntityException In case the entity is null.
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
    public static void validate(MediDataRepresentation mediDataRepresentation) throws BadEntityException {
        if (mediDataRepresentation.getCarb() == 0 || mediDataRepresentation.getGlucose() == 0) {
            throw new BadEntityException(
                    "medical data cannot be null");
        }
    }

    public static void validate(ConsultationRepresentation consultationRepresentation) throws BadEntityException {
        if (consultationRepresentation.getMedicationName() == null) {
            throw new BadEntityException(
                    "consultation name cannot be null");
        }
    }


    public static void validate(ApplicationUserRepresentation applicationUserRepresentation) throws BadEntityException {
        if ( applicationUserRepresentation.getUsername() == null || applicationUserRepresentation.getPassword() == null) {
            throw new BadEntityException(
                    "User  cannot be null");
        }
    }

    public static void validateLogin(ApplicationUserRepresentation applicationUserRepresentation) throws BadEntityException {
        if ( applicationUserRepresentation.getUsername() == null || applicationUserRepresentation.getPassword() == null) {
            throw new BadEntityException(
                    "Username and Password  cannot be null");
        }
    }

}
