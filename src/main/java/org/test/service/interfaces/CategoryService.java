package org.test.service.interfaces;


import org.test.model.Category;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@SuppressWarnings("unused")
public interface CategoryService {
    public Category getCategory(@NotNull @Min(value = 1) Long id);
    public boolean isReferenced(@NotNull @Min(value = 1) Long id);
    public Category createCategory(@NotNull @Valid Category category);
    public Category updateCategory(@NotNull @Valid Category category);
    public void deleteCategory(@NotNull @Min(value = 1) Long id);
    public List<Category> findCategoriesOrderByName();
    public List<Category> findCategoriesOrderByName(String filter);
}
