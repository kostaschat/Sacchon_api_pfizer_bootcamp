package com.pfizer.sacchonapi.router;

import com.pfizer.sacchonapi.resource.*;
import com.pfizer.sacchonapi.resource.ApplicationUser.ApplicationUserListResourceImpl;
import com.pfizer.sacchonapi.resource.ApplicationUser.ApplicationUserResourceImpl;
import com.pfizer.sacchonapi.resource.ChiefDoctor.ChiefDoctorListResourceImpl;
import com.pfizer.sacchonapi.resource.Consultation.ConsultationListResourceImpl;
import com.pfizer.sacchonapi.resource.Consultation.ConsultationResourceImpl;
import com.pfizer.sacchonapi.resource.Login.LoginResourceImpl;
import com.pfizer.sacchonapi.resource.MediData.MediDataListResourceImpl;
import com.pfizer.sacchonapi.resource.MediData.MediDataResourceImpl;
import com.pfizer.sacchonapi.resource.Patient.PatientListResourceImpl;
import com.pfizer.sacchonapi.resource.Patient.PatientUserListResourceImpl;
import org.restlet.Application;
import org.restlet.routing.Router;

public class CustomRouter {
    private Application application;

    public CustomRouter(Application application) {
        this.application = application;

    }

    public Router createApiRouter() {

        Router router = new Router(application.getContext());

        //doctor-patient remove his account
        router.attach("/remove-account", ApplicationUserResourceImpl.class);
        router.attach("/remove-account/", ApplicationUserResourceImpl.class);

        //patient browse a specific medidata
        router.attach("/medidata/{id}", MediDataResourceImpl.class);

        //doctor add a consultation to a patient
        router.attach("/add-consultation/{pid}", ConsultationListResourceImpl.class);

        //doctor browse available-new patients
        router.attach("/patients", ApplicationUserListResourceImpl.class);
        router.attach("/patients/", ApplicationUserListResourceImpl.class);

        //doctor browse his patients
        router.attach("/my-patients", PatientListResourceImpl.class);
        router.attach("/my-patients/", PatientListResourceImpl.class);

        //patient remove the modification mesagge
        router.attach("/error-modify", PatientListResourceImpl.class);
        router.attach("/error-modify/", PatientListResourceImpl.class);

        //patients with no consultation in the last month
        router.attach("/patients/no-consultation", PatientUserListResourceImpl.class);

        //doctor browse the consultations of a patient
        router.attach("/consultations/{pid}", ConsultationListResourceImpl.class);

        //doctor browse medical data of a patient
        router.attach("/all-medidata/{pid}", MediDataListResourceImpl.class);

        //doctor consults a new patient
        router.attach("/consult-patient/{pid}", ApplicationUserResourceImpl.class);

        //patient browse all his medical data
        router.attach("/medidata", MediDataListResourceImpl.class);
        router.attach("/medidata/", MediDataListResourceImpl.class);

        //patient browse all his consultations
        router.attach("/consultations", ConsultationListResourceImpl.class);
        router.attach("/consultations/", ConsultationListResourceImpl.class);

        //patient views their average daily blood glucose level over a user-specified period
        router.attach("/medidata/{datatype}/{fromdate}/{todate}", MediDataResourceImpl.class);

        //change permissions
        router.attach("/consultation/{cid}", ConsultationResourceImpl.class);

        //Reporter endpoints
        //The information submissions(personal monitor data) of a patient over a time range
        router.attach("/patient/{pid}/medidata/{fromdate}/{todate}", MediDataListResourceImpl.class);

        // /The information submissions (consultations) of a doctor over a time range
        router.attach("/doctor/{did}/consultation/{fromdate}/{todate}",ConsultationListResourceImpl.class);

        //The list of the patients who are waiting for a consultation and the time elapsed since they needed to have one
        router.attach("/patients/consultation-pending", PatientUserListResourceImpl.class);

        //chiefDoctor browse a list of the doctors with no activity over a time range
        router.attach("/inactive-doctors/{fromdate}/{todate}", ChiefDoctorListResourceImpl.class);

        //chiefDoctor browse a list of the patients with no activity over a time range
        router.attach("/patients/{fromdate}/{todate}", ApplicationUserListResourceImpl.class);
        return router;
    }

    public Router publicUser() {
        Router router = new Router();
        router.attach("/register", ApplicationUserListResourceImpl.class);
        router.attach("/register/", ApplicationUserListResourceImpl.class);
        router.attach("/login", LoginResourceImpl.class);
        router.attach("/login/", LoginResourceImpl.class);
        router.attach("/ping", PingServerResource.class);
        return router;
    }



}
