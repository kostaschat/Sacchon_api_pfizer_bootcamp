package com.pfizer.sacchonapi.router;

import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.resource.*;
import org.restlet.Application;
import org.restlet.routing.Router;

public class CustomRouter {
    private Application application;

    public CustomRouter(Application application) {
        this.application = application;

    }

    public Router createApiRouter() {

        Router router = new Router(application.getContext());

        //patient endpoints
        router.attach("/viewPatients", PatientListResourceImpl.class);
        router.attach("/viewNotActivePatients", PatientListResourceImpl.class);
        router.attach("/viewPatient/{id}", PatientResourceImpl.class);

        //medidata endpoints
        router.attach("/medidata/{id}", MediDataResourceImpl.class);
        router.attach("/medidata", MediDataListResourceImpl.class);

       // router.attach("/medidata/", MediDataListResourceImpl.class);

        router.attach("/consultation/{id}", ConsultationResourceImpl.class);
        router.attach("/consultation/", ConsultationListResourceImpl.class);
        router.attach("/consultation", ConsultationListResourceImpl.class);

        router.attach("/chief-doctor/{id}", ChiefDoctorResourceImpl.class);
        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }

}
