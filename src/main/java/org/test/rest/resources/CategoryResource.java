package org.test.rest.resources;

import org.test.model.Category;
import org.test.model.Role;
import org.test.rest.RESTUri;
import org.test.rest.Protocol;
import org.test.rest.RESTAccess;
import org.test.rest.beans.CategoryBean;
import org.test.service.interfaces.CategoryService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

import static org.test.utils.ApplicationStrings.*;

@Path("/categories")
public class CategoryResource {

    @Inject
    RESTUri restUri;

    @Context
    UriInfo uriInfo;

    @EJB
    CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public CategoryBean getCategory(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_CATEGORY_GET_AUTH,
                restUri.getUri(uriInfo, CategoryResource.class, GET_CATEGORY, id),
                Protocol.GET,
                new Role[] {Role.ADMIN, Role.USER}
        );
        Category category = categoryService.getCategory(id);
        return new CategoryBean(
                restUri.getUri(uriInfo, CategoryResource.class, GET_CATEGORY, id),
                category,
                categoryService.isReferenced(id)
        );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public CategoryBean createCategory(@Context SecurityContext securityContext, CategoryBean categoryBean) {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_CATEGORY_CREATE_AUTH,
                restUri.getUri(uriInfo, CategoryResource.class),
                Protocol.POST,
                new Role[] {Role.ADMIN}
        );
        Category category = categoryService.createCategory(new Category(categoryBean.name));
        return new CategoryBean(
                restUri.getUri(uriInfo, CategoryResource.class, GET_CATEGORY, category.getId()),
                category,
                categoryService.isReferenced(category.getId())
        );
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public CategoryBean updateCategory(@Context SecurityContext securityContext, CategoryBean categoryBean) {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_CATEGORY_UPDATE_AUTH,
                restUri.getUri(uriInfo, CategoryResource.class),
                Protocol.PUT,
                new Role[] {Role.ADMIN}
        );
        Category changedCategory = new Category();
        changedCategory.setId(categoryBean.id);
        changedCategory.setName(categoryBean.name);
        Category category = categoryService.updateCategory(changedCategory);
        return new CategoryBean(
                restUri.getUri(uriInfo, CategoryResource.class, GET_CATEGORY, category.getId()),
                category,
                categoryService.isReferenced(category.getId())
        );
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void deleteCategory(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_CATEGORY_DELETE_AUTH,
                restUri.getUri(uriInfo, CategoryResource.class, GET_CATEGORY, id),
                Protocol.DELETE,
                new Role[] {Role.ADMIN}
        );
        categoryService.deleteCategory(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CategoryBean> getCategories(@Context SecurityContext securityContext, @QueryParam(FILTER) String filter) {
        RESTAccess.checkRolesAllowed(securityContext,
                DETAIL_CATEGORY_GET_AUTH,
                restUri.getUri(uriInfo, CategoryResource.class),
                Protocol.GET,
                new Role[] {Role.ADMIN, Role.USER}
        );
        List<Category> categories = categoryService.findCategoriesOrderByName(filter);
        List<CategoryBean> categoryBeans = new ArrayList<>();
        for(Category category : categories) {
            categoryBeans.add(new CategoryBean(
                    restUri.getUri(uriInfo, CategoryResource.class, GET_CATEGORY, category.getId()),
                    category,
                    categoryService.isReferenced(category.getId())
            ));
        }
        return categoryBeans;
    }
}
