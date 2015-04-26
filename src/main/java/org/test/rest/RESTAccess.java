package org.test.rest;

import org.test.error.beans.ErrorDetailContext;
import org.test.error.beans.ErrorDetailREST;
import org.test.model.Role;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import static org.test.utils.ApplicationStrings.*;

public class RESTAccess {
    public static boolean checkRolesAllowed(@Context SecurityContext securityContext,
                                            String message,
                                            String resource,
                                            Protocol protocol,
                                            Role[] roles) {
        boolean allowed = false;
        for(Role role : roles) {
            if(securityContext.isUserInRole(role.getDescription())) {
                allowed = true;
                break;
            }
        }
        if(!allowed) {
            RESTException e = new RESTException(ERROR_ROLE_ACCESS, MESSAGE_ROLE_ACCESS);
            ErrorDetailREST errorDetailREST = new ErrorDetailREST(message, resource, protocol, roles);
            errorDetailREST.setRoles(roles);
            e.getErrorWrapper().addDetail(errorDetailREST);
            throw e;
        }
        return true;
    }
    public static boolean checkUserContext(@Context SecurityContext securityContext,
                                            String message,
                                            String resource,
                                            Protocol protocol,
                                            String user) {
        if(securityContext.isUserInRole(Role.USER.getDescription())) {
            boolean isTheUser = false;
            if (securityContext.getUserPrincipal().getName().toUpperCase().equals(user.toUpperCase())) {
                isTheUser = true;
            }
            if (!isTheUser) {
                RESTException e = new RESTException(ERROR_USER_CONTEXT, MESSAGE_USER_CONTEXT);
                ErrorDetailContext errorDetailContext = new ErrorDetailContext(message, resource, protocol, user);
                e.getErrorWrapper().addDetail(errorDetailContext);
                throw e;
            }
        }
        return true;
    }
}
