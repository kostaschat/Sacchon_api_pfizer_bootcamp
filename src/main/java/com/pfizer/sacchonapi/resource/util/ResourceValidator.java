package com.pfizer.sacchonapi.resource.util;

import com.pfizer.sacchonapi.exception.BadEntityException;
import com.pfizer.sacchonapi.model.Patient;
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

    public static void checkUserValues(String firstName, String lastName, String password, String email) throws BadEntityException
    {
        if (firstName == null || lastName == null || password == null || email == null ) {
            throw new BadEntityException(
                    "The required fields cannot not be empty");
        }
    }

    public static void validatePatient(PatientRepresentation patientRepresentation) throws BadEntityException {
        checkUserValues(patientRepresentation.getFirstName(), patientRepresentation.getLastName(),
                patientRepresentation.getPassword(),
                patientRepresentation.getEmail());
    }

    public static void validateChiefDoctor(ChiefDoctorRepresentation chiefDoctorRepresentation) throws BadEntityException {
        checkUserValues(chiefDoctorRepresentation.getFirstName(), chiefDoctorRepresentation.getLastName(),
                chiefDoctorRepresentation.getPassword(),
                chiefDoctorRepresentation.getEmail());
    }

    public static void validateDoctor(DoctorRepresentation doctorRepresentation) throws BadEntityException {
        checkUserValues(doctorRepresentation.getFirstName(), doctorRepresentation.getLastName(),
                doctorRepresentation.getPassword(),
                doctorRepresentation.getEmail());
    }
}
