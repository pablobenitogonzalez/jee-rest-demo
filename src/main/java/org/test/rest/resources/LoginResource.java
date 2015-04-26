package org.test.rest.resources;

import org.test.model.Login;
import org.test.model.Role;
import org.test.rest.beans.LoginBean;
import org.test.rest.Protocol;
import org.test.rest.RESTAccess;
import org.test.rest.RESTUri;
import org.test.rest.beans.AuthAccessBean;
import org.test.service.interfaces.LoginService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.security.NoSuchAlgorithmException;

import static org.test.utils.ApplicationStrings.*;

@Path("/login")
public class LoginResource {

    @Inject
    RESTUri restUri;

    @Context
    UriInfo uriInfo;

    @EJB
    LoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public AuthAccessBean createLogin(@Context SecurityContext securityContext, LoginBean loginBean) throws NoSuchAlgorithmException {
        Login login = new Login();
        login.setEmail(loginBean.email);
        login.setPassword(loginBean.password);
        if(securityContext.isUserInRole(Role.ADMIN.getDescription())) { // admin
            login.setRole(loginBean.role);
        } else {
            login.setRole(Role.USER);
        }
        Login loginCreated = loginService.createLogin(login);
        return new AuthAccessBean(loginCreated);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public AuthAccessBean updateLogin(@Context SecurityContext securityContext, LoginBean loginBean) throws NoSuchAlgorithmException {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_LOGIN_UPDATE_AUTH,
                restUri.getUri(uriInfo, LoginResource.class),
                Protocol.PUT,
                new Role[]{Role.ADMIN, Role.USER}
        );
        RESTAccess.checkUserContext(
                securityContext,
                DETAIL_NOT_THE_SAME_USER_CONTEXT,
                restUri.getUri(uriInfo, LoginResource.class),
                Protocol.PUT,
                loginBean.email
        );
        Login login = loginService.getLogin(loginBean.id);
        login.setEmail(loginBean.email);
        login.setPassword(loginBean.password);
        if(securityContext.isUserInRole(Role.ADMIN.getDescription())) { // admin
            login.setRole(loginBean.role);
        } else {
            login.setRole(Role.USER);
        }
        Login loginUpdated = loginService.updateLogin(login);
        return new AuthAccessBean(loginUpdated);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void deleteLogin(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_LOGIN_DELETE_AUTH,
                restUri.getUri(uriInfo, LoginResource.class, DELETE_LOGIN, id),
                Protocol.DELETE,
                new Role[]{Role.ADMIN}
        );
        loginService.deleteLogin(id);
    }
}
