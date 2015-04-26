package org.test.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.test.error.beans.ErrorDetailService;
import org.test.model.Login;
import org.test.model.Role;
import org.test.persistence.interfaces.LoginDao;
import org.test.persistence.jpa.JpaDao;
import org.test.persistence.jpa.LoginJpaDao;
import org.test.service.beans.LoginServiceBean;
import org.test.service.interfaces.LoginService;
import org.test.utils.ApplicationException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.test.utils.ApplicationStringsTest.LOGGER_END;
import static org.test.utils.ApplicationStringsTest.LOGGER_START;

@RunWith(Arquillian.class)
@Transactional
public class LoginServiceTest {

    private static Log logger = LogFactory.getLog(LoginServiceTest.class);

    @EJB
    private LoginService loginService;

    @Inject
    private RoleAdmin admin;

    @Inject
    private RoleUser user;

    @Deployment
    public static Archive<?> deployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(ApplicationException.class.getPackage())
                .addPackage(ErrorDetailService.class.getPackage())
                .addPackage(Login.class.getPackage())
                .addPackage(JpaDao.class.getPackage())
                .addPackage(LoginDao.class.getPackage())
                .addPackage(LoginJpaDao.class.getPackage())
                .addPackage(LoginService.class.getPackage())
                .addPackage(LoginServiceBean.class.getPackage())
                .addPackage(RoleAdmin.class.getPackage())
                .addPackage(RoleUser.class.getPackage())
                .addAsResource("persistence-datasource.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getLoginTest() throws Exception {
        admin.call(new Callable<Login>() {
            @Override
            public Login call() throws Exception {
                logger.info(LOGGER_START + "getLoginTest");
                Login login = loginService.getLogin(1L);
                assertNotNull(login);
                assertTrue(login.getId() == 1L);
                logger.info(LOGGER_END);
                return null;
            }
        });
    }

    @Test(expected = EJBException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void getLoginNotFoundTest() throws Exception {
        admin.call(new Callable<Login>() {
            @Override
            public Login call() throws Exception {
                logger.info(LOGGER_START + "getLoginNotFoundTest");
                try {
                    loginService.getLogin(99L);
                    return null;
                } catch (EJBException e) {
                    ApplicationException exception = (ApplicationException) e.getCausedByException();
                    logger.info(exception.getErrorWrapper().toString());
                    logger.info(LOGGER_END);
                    throw e;
                }
            }
        });
    }

    @Test(expected = EJBException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void createLoginRepeatedNaturalKeyTest() throws Exception {
        admin.call(new Callable<Login>() {
            @Override
            public Login call() throws Exception {
                logger.info(LOGGER_START + "createLoginRepeatedNaturalKeyTest");
                try {
                    Login login = new Login("iamuser@gmail.com", "1234", Role.USER);
                    loginService.createLogin(login);
                    return null;
                } catch (EJBException e) {
                    ApplicationException exception = (ApplicationException) e.getCausedByException();
                    logger.info(exception.getErrorWrapper().toString());
                    logger.info(LOGGER_END);
                    throw e;
                }
            }
        });
    }
}