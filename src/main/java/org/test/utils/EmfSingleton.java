package org.test.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.test.utils.ApplicationStrings.PERSISTENCE_UNIT_NAME;

public class EmfSingleton {
    private static EmfSingleton ourInstance = new EmfSingleton();
    private EntityManagerFactory emf = null;

    public static EmfSingleton getInstance() {
        return ourInstance;
    }

    private EmfSingleton() {}

    public EntityManagerFactory getEmf() {
        if (this.emf == null) this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        return this.emf;
    }
}