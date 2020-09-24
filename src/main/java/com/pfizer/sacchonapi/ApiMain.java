package com.pfizer.sacchonapi;

import com.pfizer.sacchonapi.repository.util.JpaUtil;
import com.pfizer.sacchonapi.router.CustomRouter;
import com.pfizer.sacchonapi.security.Shield;
import com.pfizer.sacchonapi.security.cors.CorsFilter;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.Role;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

public class ApiMain extends Application {
    public static final Logger LOGGER = Engine.getLogger(ApiMain.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("Contacts application starting...");
        EntityManager em   = JpaUtil.getEntityManager();

        Component c = new Component();
        c.getServers().add(Protocol.HTTP, 9000);
        c.getDefaultHost().attach("/v1", new ApiMain());
        c.start();

        LOGGER.info("Sample Web API started");
        LOGGER.info("URL: http://localhost:9000/v1/medidata");
        LOGGER.info("URL: http://localhost:9000/v1/consultation");
        LOGGER.info("URL: http://localhost:9000/v1/register");

    }
    public ApiMain() {

        setName("WebAPITutorial");
        setDescription("Full Web API tutorial");

        getRoles().add(new Role(this, Shield.chiefDoctor));
        getRoles().add(new Role(this, Shield.doctor));
        getRoles().add(new Role(this, Shield.patient));

    }

    @Override
    public Restlet createInboundRoot() {

        CustomRouter customRouter = new CustomRouter(this);
        Shield shield = new Shield(this);

//        Router publicRouter = customRouter.publicResources();
        Router publicUser = customRouter.publicUser();
        Router apiRouter = customRouter.createApiRouter();

        ChallengeAuthenticator apiGuard = shield.createApiGuard();
        // Create the api router, protected by a guard

        apiGuard.setNext(apiRouter);

        publicUser.attachDefault(apiGuard);

        CorsFilter corsFilter = new CorsFilter(this);
        return corsFilter.createCorsFilter(publicUser);
    }
}
