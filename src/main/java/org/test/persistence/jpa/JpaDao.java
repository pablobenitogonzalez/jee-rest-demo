package org.test.persistence.jpa;


import org.test.persistence.interfaces.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.test.utils.ApplicationStrings.PERSISTENCE_UNIT_NAME;

public abstract class JpaDao<T, K> implements GenericDao<T, K> {

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    public JpaDao() {}

    public JpaDao(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    public T create(T t) {
        em.persist(t);
        em.flush();
        em.refresh(t);
        return t;
    }

    public T update(T t) {
        return (T) em.merge(t);
    }

    public void delete(T t) {
        t = em.merge(t);
        em.remove(t);
    }
}
