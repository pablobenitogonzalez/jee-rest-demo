package org.test.service;

import org.test.utils.ApplicationStrings;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import java.util.concurrent.Callable;

@Stateless
@RunAs(ApplicationStrings.ROLE_ADMIN)
@PermitAll
public class RoleAdmin {
    public <V> V call(Callable<V> callable) throws Exception {
        return callable.call();
    }
}
