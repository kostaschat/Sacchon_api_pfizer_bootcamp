package com.pfizer.sacchonapi.security;

import org.restlet.Application;
import org.restlet.data.ChallengeScheme;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MemoryRealm;
import org.restlet.security.User;
import org.restlet.security.Verifier;


public class Shield {
    public static final String chiefDoctor = "chiefDoctor";
    public static final String doctor = "doctor";
    public static final String patient = "patient";


    private Application application;

    public Shield(Application application) {
        this.application = application;
    }


    public ChallengeAuthenticator createApiGuard() {

        ChallengeAuthenticator apiGuard = new ChallengeAuthenticator(
                application.getContext(), ChallengeScheme.HTTP_BASIC, "realm");

        Verifier verifier = new CustomVerifier();
        apiGuard.setVerifier(verifier);

        return apiGuard;
    }

    /**
     * not used
     * it is given as reference for hard-coded definition of users
     * @return
     */
    public ChallengeAuthenticator createApiGuard1() {

        ChallengeAuthenticator apiGuard = new ChallengeAuthenticator(
                application.getContext(), ChallengeScheme.HTTP_BASIC, "realm");

        MemoryRealm realm = new MemoryRealm();

        User doctor = new User("doctor", "doctor");
        realm.getUsers().add(doctor);
        realm.map(doctor, application.getRole(Shield.doctor));
        realm.map(doctor, application.getRole(patient));

        User chiefDoctor = new User("chiefDoctor", "chiefDoctor");
        realm.getUsers().add(chiefDoctor);
        realm.map(chiefDoctor, application.getRole(Shield.chiefDoctor));
        realm.map(chiefDoctor, application.getRole(Shield.chiefDoctor));
        realm.map(chiefDoctor, application.getRole(Shield.chiefDoctor));

        User patient = new User("patient", "patient");
        realm.getUsers().add(patient);
        realm.map(patient, application.getRole(Shield.patient));

        apiGuard.setVerifier(realm.getVerifier());
        apiGuard.setEnroler(realm.getEnroler());

        return apiGuard;
    }
}
