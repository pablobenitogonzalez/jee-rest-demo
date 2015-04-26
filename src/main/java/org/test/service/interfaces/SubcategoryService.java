package org.test.service.interfaces;

import org.test.model.Subcategory;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@SuppressWarnings("unused")
public interface SubcategoryService {
    public Subcategory getSubcategory(@NotNull @Min(value = 1) Long id);
    public Subcategory createSubcategory(@NotNull @Valid Subcategory subcategory);
    public Subcategory updateSubcategory(@NotNull @Valid Subcategory subcategory);
    public void deleteSubcategory(@NotNull @Min(value = 1) Long id);
    public List<Subcategory> findSubcategoriesOrderByName();
    public List<Subcategory> findSubcategoriesOrderByName(String filter);
}
