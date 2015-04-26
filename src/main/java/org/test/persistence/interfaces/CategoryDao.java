package org.test.persistence.interfaces;

import org.test.model.Category;

import java.util.List;

public interface CategoryDao extends GenericDao<Category, Long> {
    public Boolean existsNaturalKey(String nombre);
    public Boolean hasSubcategories(Long idCategoria);
    public List<Category> findAllOrderByName();
    public List<Category> findAllOrderByName(String filter);
}
