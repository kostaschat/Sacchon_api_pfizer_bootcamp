package com.pfizer.sacchonapi.router;

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

        router.attach("/medidata/{id}", MediDataResourceImpl.class);
        router.attach("/medidata", MediDataListResourceImpl.class);
        router.attach("/medidata/", MediDataListResourceImpl.class);

        router.attach("/doctor/{id}", DoctorResourceImpl.class);
        router.attach("/doctor", DoctorListResourceImpl.class);
        router.attach("/doctor/", DoctorListResourceImpl.class);




        return router;
    }
    //dasddas

    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }

}
