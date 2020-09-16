package com.pfizer.sacchonapi.router;

import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.resource.MediDataResourceImpl;
import com.pfizer.sacchonapi.resource.PingServerResource;
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
//        router.attach("/product", ProductListResourceImpl.class);
////        router.attach("/product/", ProductListResourceImpl.class);


        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }

}
