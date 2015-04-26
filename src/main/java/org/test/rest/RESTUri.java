package org.test.rest;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class RESTUri {

    public String getUri(UriInfo uriInfo, Class c) {
        UriBuilder baseUriBuilder = uriInfo.getBaseUriBuilder();
        return baseUriBuilder.path(c).build().toString();
    }

    public String getUri(UriInfo uriInfo, Class c, String method, Long id) {
        UriBuilder baseUriBuilder = uriInfo.getBaseUriBuilder();
        return baseUriBuilder.path(c).path(c, method).build(id).toString();
    }
}
