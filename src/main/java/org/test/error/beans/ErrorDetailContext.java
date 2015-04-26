package org.test.error.beans;

import org.test.rest.Protocol;

public class ErrorDetailContext extends GenericErrorDetail {
    private String resource;
    private Protocol protocol;
    private String email;

    public ErrorDetailContext(String message, String resource, Protocol protocol, String email) {
        super(message);
        this.resource = resource;
        this.protocol = protocol;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ErrorDetailContext{" +
                "message='" + super.getMessage() + '\'' +
                ", resource='" + resource + '\'' +
                ", protocol=" + protocol +
                ", email='" + email + '\'' +
                '}';
    }
}
