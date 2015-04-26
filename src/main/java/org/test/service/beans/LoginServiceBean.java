package org.test.service.beans;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.test.error.beans.ErrorDetailService;
import org.test.error.beans.GenericErrorDetail;
import org.test.model.Login;
import org.test.persistence.interfaces.LoginDao;
import org.test.persistence.jpa.Jpa;
import org.test.service.interfaces.LoginService;
import org.test.utils.ApplicationException;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;

import static org.test.utils.ApplicationStrings.*;

@Stateless
@Local(LoginService.class)
@SecurityDomain(SECURITY_DOMAIN)
public class LoginServiceBean implements LoginService {

    @Inject @Jpa
    private LoginDao loginDao;

    @Resource
    private SessionContext context;

    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public Login getLogin(@NotNull @Min(value = 1) Long id) {
        Login login = loginDao.find(id);
        if(login == null) {
            ApplicationException e = new ApplicationException(ERROR_NOT_FOUND, MESSAGE_NOT_FOUND);
            e.getErrorWrapper().addDetail(new ErrorDetailService(LOGIN, ID, DETAIL_LOGIN_NOT_FOUND));
            throw e;
        }
        return login;
    }

    @PermitAll
    public Login createLogin(@NotNull @Valid Login login) {
        if(loginDao.existsNaturalKey(login.getEmail())) {
            ApplicationException e = new ApplicationException(ERROR_UNIQUE_KEY, MESSAGE_UNIQUE_KEY);
            e.getErrorWrapper().addDetail(new ErrorDetailService(LOGIN, NK_LOGIN, DETAIL_LOGIN_UNIQUE_KEY));
            throw e;
        }
        return loginDao.create(login);
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public Login updateLogin(@NotNull @Valid Login login) {
        if(context.isCallerInRole(ROLE_USER) && !context.getCallerPrincipal().getName().toUpperCase().equals(login.getEmail())) {
            ApplicationException e = new ApplicationException(ERROR_USER_CONTEXT, MESSAGE_USER_CONTEXT);
            e.getErrorWrapper().addDetail(new GenericErrorDetail(DETAIL_NOT_THE_SAME_USER_CONTEXT));
            throw e;
        }
        Login oldLogin = this.getLogin(login.getId());
        if(!oldLogin.getEmail().equals(login.getEmail()) && loginDao.existsNaturalKey(login.getEmail())) {
            ApplicationException e = new ApplicationException(ERROR_UNIQUE_KEY, MESSAGE_UNIQUE_KEY);
            e.getErrorWrapper().addDetail(new ErrorDetailService(LOGIN, NK_LOGIN, DETAIL_LOGIN_UNIQUE_KEY));
            throw e;
        }
        login.getRecord().setCreated(oldLogin.getRecord().getCreated());
        return loginDao.update(login);
    }

    @RolesAllowed({ROLE_ADMIN})
    public void deleteLogin(@NotNull @Min(value = 1) Long id) {
        Login login = this.getLogin(id);
        loginDao.delete(login);
    }

    @PermitAll
    public Login findLoginByEmailAndPassword(@NotNull String email, @NotNull String password) throws NoSuchAlgorithmException {
        return loginDao.findByEmailAndPassword(email, password);
    }
}
