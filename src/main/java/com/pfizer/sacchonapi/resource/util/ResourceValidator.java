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

    public static void validatePatient(PatientRepresentation patientRepresentation) throws BadEntityException {
        if ( patientRepresentation.getFirstName() == null || patientRepresentation.getLastName() == null || patientRepresentation.getPassword() == null || patientRepresentation.getEmail() == null ) {
            throw new BadEntityException(
                    "The required fields cannot not be empty");
        }
    }

    public static void validateChiefDoctor(ChiefDoctorRepresentation chiefDoctorRepresentation) throws BadEntityException {
        if ( chiefDoctorRepresentation.getFirstName() == null || chiefDoctorRepresentation.getLastName() == null || chiefDoctorRepresentation.getPassword() == null || chiefDoctorRepresentation.getEmail() == null ) {
            throw new BadEntityException(
                    "The required fields cannot not be empty");
        }
    }

    public static void validate(DoctorRepresentation doctorRepresentation) throws BadEntityException {
        if ( doctorRepresentation.isActive()==false) {
            throw new BadEntityException(
                    "doctor  cannot be null");
        }
    }
}
