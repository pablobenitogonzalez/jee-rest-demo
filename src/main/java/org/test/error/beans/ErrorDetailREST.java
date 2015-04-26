package org.test.error.beans;

import org.test.model.Role;
import org.test.rest.Protocol;

import java.util.Arrays;

public class ErrorDetailREST extends GenericErrorDetail {
    private String resource;
    private Protocol protocol;
    private Role[] roles;


    public ErrorDetailREST(String message, String resource, Protocol protocol, Role[] roles) {
        super(message);
        this.resource = resource;
        this.protocol = protocol;
        this.roles = roles;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "ErrorDetailREST{" +
                "message='" + super.getMessage() + '\'' +
                ", resource='" + resource + '\'' +
                ", protocol=" + protocol +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}
