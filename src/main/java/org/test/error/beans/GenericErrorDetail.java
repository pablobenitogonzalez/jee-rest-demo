package org.test.error.beans;

import java.io.Serializable;

public class GenericErrorDetail implements Serializable {
    private String message;

    public GenericErrorDetail(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GenericErrorDetail{" +
                "message='" + message + '\'' +
                '}';
    }
}
