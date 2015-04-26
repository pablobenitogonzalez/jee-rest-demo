package org.test.service.beans;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.test.error.beans.ErrorDetailService;
import org.test.model.Category;
import org.test.model.Subcategory;
import org.test.persistence.interfaces.SubcategoryDao;
import org.test.persistence.jpa.Jpa;
import org.test.service.interfaces.CategoryService;
import org.test.service.interfaces.SubcategoryService;
import org.test.utils.ApplicationException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.test.utils.ApplicationStrings.*;

@Stateless
@Local(SubcategoryService.class)
@SecurityDomain(SECURITY_DOMAIN)
public class SubcategoryServiceBean implements SubcategoryService {

    @EJB
    private CategoryService categoryService;

    @Inject @Jpa
    private SubcategoryDao subcategoryDao;

    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public Subcategory getSubcategory(@NotNull @Min(value=1) Long id) {
        Subcategory subcategory = subcategoryDao.find(id);
        if(subcategory == null) {
            ApplicationException e = new ApplicationException(ERROR_NOT_FOUND, MESSAGE_NOT_FOUND);
            e.getErrorWrapper().addDetail(new ErrorDetailService(SUBCATEGORY, ID, DETAIL_SUBCATEGORY_NOT_FOUND));
            throw e;
        }
        return subcategory;
    }

    @RolesAllowed({ROLE_ADMIN})
    public Subcategory createSubcategory(@NotNull @Valid Subcategory subcategory) {
        Category category = categoryService.getCategory(subcategory.getCategory().getId());
        if(subcategoryDao.existsNaturalKey(subcategory.getName(), category)) {
            ApplicationException e = new ApplicationException(ERROR_NOT_FOUND, MESSAGE_UNIQUE_KEY);
            e.getErrorWrapper().addDetail(new ErrorDetailService(SUBCATEGORY, NK_SUBCATEGORY, DETAIL_SUBCATEGORY_UNIQUE_KEY));
            throw e;
        }
        return subcategoryDao.create(subcategory);
    }

    @RolesAllowed({ROLE_ADMIN})
    public Subcategory updateSubcategory(@NotNull @Valid Subcategory subcategory) {
        Category newCategory = categoryService.getCategory(subcategory.getCategory().getId());
        Subcategory oldSubcategory = this.getSubcategory(subcategory.getId());
        if(!(oldSubcategory.getName().equals(subcategory.getName()) && oldSubcategory.getCategory().equals(newCategory))
                && subcategoryDao.existsNaturalKey(subcategory.getName(), subcategory.getCategory())) {
            ApplicationException e = new ApplicationException(ERROR_NOT_FOUND, MESSAGE_UNIQUE_KEY);
            e.getErrorWrapper().addDetail(new ErrorDetailService(SUBCATEGORY, NK_SUBCATEGORY, DETAIL_SUBCATEGORY_UNIQUE_KEY));
            throw e;
        }
        subcategory.getRecord().setCreated(oldSubcategory.getRecord().getCreated());
        return subcategoryDao.update(subcategory);
    }

    @RolesAllowed({ROLE_ADMIN})
    public void deleteSubcategory(@NotNull @Min(value=1) Long id) {
        Subcategory subcategory = this.getSubcategory(id);
        subcategoryDao.delete(subcategory);
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public List<Subcategory> findSubcategoriesOrderByName() {
        return subcategoryDao.findAllOrderByName();
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public List<Subcategory> findSubcategoriesOrderByName(String filter) {
        return subcategoryDao.findAllOrderByName(filter);
    }
}
