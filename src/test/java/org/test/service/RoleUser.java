package org.test.service;

import org.test.utils.ApplicationStrings;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import java.util.concurrent.Callable;

@Stateless
@RunAs(ApplicationStrings.ROLE_USER)
@PermitAll
public class RoleUser {
    public <V> V call(Callable<V> callable) throws Exception {
        return callable.call();
    }
}
