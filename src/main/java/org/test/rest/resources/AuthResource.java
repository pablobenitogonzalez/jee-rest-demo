package org.test.rest.resources;

import org.test.error.beans.ErrorDetailAuth;
import org.test.model.Login;
import org.test.rest.RESTException;
import org.test.rest.beans.AuthAccessBean;
import org.test.rest.beans.AuthLoginBean;
import org.test.service.interfaces.LoginService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;

import static org.test.utils.ApplicationStrings.*;

@Path("/auth")
public class AuthResource {

    @EJB
    LoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public AuthAccessBean login(AuthLoginBean authLoginBean) throws NoSuchAlgorithmException {
        Login login = loginService.findLoginByEmailAndPassword(authLoginBean.email, authLoginBean.password);
        if(login == null) {
            RESTException e = new RESTException(ERROR_LOGIN_ACCESS, MESSAGE_LOGIN_ACCESS);
            ErrorDetailAuth errorDetailAuth = new ErrorDetailAuth(DETAIL_BAD_AUTH, authLoginBean.email, authLoginBean.password);
            e.getErrorWrapper().addDetail(errorDetailAuth);
            throw e;
        }
        return new AuthAccessBean(login);
    }
}
