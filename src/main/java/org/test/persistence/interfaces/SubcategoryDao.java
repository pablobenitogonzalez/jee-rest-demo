package org.test.persistence.interfaces;

import org.test.model.Category;
import org.test.model.Subcategory;

import java.util.List;

public interface SubcategoryDao extends GenericDao<Subcategory, Long> {
    public Boolean existsNaturalKey(String nombre, Category category);
    public List<Subcategory> findAllOrderByName();
    public List<Subcategory> findAllOrderByName(String filter);
}
