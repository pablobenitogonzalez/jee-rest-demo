package org.test.rest.resources;

import org.test.model.Category;
import org.test.model.Role;
import org.test.model.Subcategory;
import org.test.rest.Protocol;
import org.test.rest.RESTAccess;
import org.test.rest.RESTUri;
import org.test.rest.beans.SubcategoryBean;
import org.test.service.interfaces.CategoryService;
import org.test.service.interfaces.SubcategoryService;

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

@Path("/subcategories")
public class SubcategoryResource {

    @Inject
    RESTUri restUri;

    @Context
    UriInfo uriInfo;

    @EJB
    CategoryService categoryService;

    @EJB
    SubcategoryService subcategoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public SubcategoryBean getSubcategory(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_SUBCATEGORY_GET_AUTH,
                restUri.getUri(uriInfo, SubcategoryResource.class, GET_SUBCATEGORY, id),
                Protocol.GET,
                new Role[]{Role.ADMIN, Role.USER}
        );
        Subcategory subcategory = subcategoryService.getSubcategory(id);
        return new SubcategoryBean(
                restUri.getUri(uriInfo, SubcategoryResource.class, GET_SUBCATEGORY, id),
                subcategory,
                subcategory.getCategory().getId()
        );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public SubcategoryBean createSubcategory(@Context SecurityContext securityContext, SubcategoryBean subcategoryBean) {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_SUBCATEGORY_CREATE_AUTH,
                restUri.getUri(uriInfo, SubcategoryResource.class),
                Protocol.POST,
                new Role[] {Role.ADMIN}
        );
        Category category = categoryService.getCategory(subcategoryBean.category);
        Subcategory subcategory = subcategoryService.createSubcategory(new Subcategory(subcategoryBean.name, category));
        return new SubcategoryBean(
                restUri.getUri(uriInfo, SubcategoryResource.class, GET_SUBCATEGORY, subcategory.getId()),
                subcategory,
                category.getId()
        );
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public SubcategoryBean updateSubcategory(@Context SecurityContext securityContext, SubcategoryBean subcategoryBean) {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_SUBCATEGORY_UPDATE_AUTH,
                restUri.getUri(uriInfo, SubcategoryResource.class),
                Protocol.PUT,
                new Role[] {Role.ADMIN}
        );
        Category category = categoryService.getCategory(subcategoryBean.category);
        Subcategory changedSubcategory = new Subcategory();
        changedSubcategory.setId(subcategoryBean.id);
        changedSubcategory.setName(subcategoryBean.name);
        changedSubcategory.setCategory(category);
        Subcategory subcategory = subcategoryService.updateSubcategory(changedSubcategory);
        return new SubcategoryBean(
                restUri.getUri(uriInfo, SubcategoryResource.class, GET_SUBCATEGORY, subcategory.getId()),
                subcategory,
                category.getId()
        );
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void deleteSubcategory(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_SUBCATEGORY_DELETE_AUTH,
                restUri.getUri(uriInfo, SubcategoryResource.class, GET_SUBCATEGORY, id),
                Protocol.DELETE,
                new Role[] {Role.ADMIN}
        );
        subcategoryService.deleteSubcategory(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SubcategoryBean> getSubcategories(@Context SecurityContext securityContext, @QueryParam(FILTER) String filter) {
        RESTAccess.checkRolesAllowed(
                securityContext,
                DETAIL_SUBCATEGORY_GET_AUTH,
                restUri.getUri(uriInfo, SubcategoryResource.class),
                Protocol.GET,
                new Role[] {Role.ADMIN, Role.USER}
        );
        List<Subcategory> subcategories = subcategoryService.findSubcategoriesOrderByName(filter);
        List<SubcategoryBean> subcategoryBeans = new ArrayList<>();
        for(Subcategory subcategory : subcategories) {
            subcategoryBeans.add(new SubcategoryBean(
                    restUri.getUri(uriInfo, SubcategoryResource.class, GET_SUBCATEGORY, subcategory.getCategory().getId()),
                    subcategory,
                    subcategory.getCategory().getId()
            ));
        }
        return subcategoryBeans;
    }
}
