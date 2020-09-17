package com.pfizer.sacchonapi.security;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class ResourceUtils {
    /**
     * Indicates if the authenticated client user associated to the current
     * request is in the given role name.
     *
     * @param serverResource
     *            The current server resource.
     * @param role
     *            The role to check.
     * @throws ResourceException
     *             In case the current authenticated user has not sufficient
     *             permission.
     */
    public static void checkRole(ServerResource serverResource, String role)
            throws ResourceException {
        if (!serverResource.isInRole(role)) {
            throw new ResourceException(
                    Status.CLIENT_ERROR_FORBIDDEN.getCode(),
                    "You're not authorized to send this call.");
        }
    }

    public static void checkRoles(ServerResource serverResource, String role1, String role2)
            throws ResourceException {
        if (!serverResource.isInRole(role1) && !serverResource.isInRole(role2)){
            throw new ResourceException(
                    Status.CLIENT_ERROR_FORBIDDEN.getCode(),
                    "You're not authorized to send this call.");
        }
    }
}
