package org.test.rest;

public enum Protocol {

    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    private final String description;

    private Protocol(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
