package org.test.persistence.interfaces;

import org.test.model.Login;

import java.security.NoSuchAlgorithmException;

public interface LoginDao extends GenericDao<Login, Long> {
    public Boolean existsNaturalKey(String email);
    public Login findByEmailAndPassword(String email, String password) throws NoSuchAlgorithmException;
}
