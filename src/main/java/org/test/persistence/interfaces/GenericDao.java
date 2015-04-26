package org.test.persistence.interfaces;

public interface GenericDao<T, K> {
    public T create(T t);
    public T update(T t);
    public void delete(T t);
    public T find(K id);
}
