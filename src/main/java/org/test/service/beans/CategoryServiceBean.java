package org.test.service.beans;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.test.error.beans.ErrorDetailService;
import org.test.model.Category;
import org.test.persistence.interfaces.CategoryDao;
import org.test.persistence.jpa.Jpa;
import org.test.service.interfaces.CategoryService;
import org.test.utils.ApplicationException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.test.utils.ApplicationStrings.*;

@Stateless
@Local(CategoryService.class)
@SecurityDomain(SECURITY_DOMAIN)
public class CategoryServiceBean implements CategoryService {

    @Inject @Jpa
    private CategoryDao categoryDao;

    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public Category getCategory(@NotNull @Min(value=1) Long id) {
        Category category = categoryDao.find(id);
        if(category == null) {
            ApplicationException e = new ApplicationException(ERROR_NOT_FOUND, MESSAGE_NOT_FOUND);
            e.getErrorWrapper().addDetail(new ErrorDetailService(CATEGORY, ID, DETAIL_CATEGORY_NOT_FOUND));
            throw e;
        }
        return category;
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public boolean isReferenced(@NotNull @Min(value=1) Long id) {
        Category category = this.getCategory(id);
        return categoryDao.hasSubcategories(category.getId());
    }

    @RolesAllowed({ROLE_ADMIN})
    public Category createCategory(@NotNull @Valid Category category) {
        if(categoryDao.existsNaturalKey(category.getName())) {
            ApplicationException e = new ApplicationException(ERROR_UNIQUE_KEY, MESSAGE_UNIQUE_KEY);
            e.getErrorWrapper().addDetail(new ErrorDetailService(CATEGORY, NK_CATEGORY, DETAIL_CATEGORY_UNIQUE_KEY));
            throw e;
        }
        return categoryDao.create(category);
    }

    @RolesAllowed({ROLE_ADMIN})
    public Category updateCategory(@NotNull @Valid Category category) {
        Category oldCategory = this.getCategory(category.getId());
        if(!oldCategory.getName().equals(category.getName()) && categoryDao.existsNaturalKey(category.getName())) {
            ApplicationException e = new ApplicationException(ERROR_UNIQUE_KEY, MESSAGE_UNIQUE_KEY);
            e.getErrorWrapper().addDetail(new ErrorDetailService(CATEGORY, NK_CATEGORY, DETAIL_CATEGORY_UNIQUE_KEY));
            throw e;
        }
        category.getRecord().setCreated(oldCategory.getRecord().getCreated());
        return categoryDao.update(category);
    }

    @RolesAllowed({ROLE_ADMIN})
    public void deleteCategory(@NotNull @Min(value=1) Long id) {
        Category category = this.getCategory(id);
        if(this.isReferenced(id)) {
            ApplicationException e = new ApplicationException(ERROR_REFERENTIAL_INTEGRITY, MESSAGE_REFERENTIAL_INTEGRITY);
            e.getErrorWrapper().addDetail(new ErrorDetailService(CATEGORY, SUBCATEGORY, DETAIL_CATEGORY_REFERENTIAL_INTEGRITY));
            throw e;
        }
        categoryDao.delete(category);
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public List<Category> findCategoriesOrderByName() {
        return categoryDao.findAllOrderByName();
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public List<Category> findCategoriesOrderByName(String filter) {
        return categoryDao.findAllOrderByName(filter);
    }
}
