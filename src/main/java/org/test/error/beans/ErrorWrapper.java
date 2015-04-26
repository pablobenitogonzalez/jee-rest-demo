package org.test.error.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorWrapper implements Serializable {
    private String error;
    private String message;
    private List<GenericErrorDetail> details = new ArrayList<>();

    public ErrorWrapper() {}

    public ErrorWrapper(String error, String message) {
        this.error = (error == null)? null : error.toLowerCase();
        this.message = (message == null)? null : message.toLowerCase();
    }

    public ErrorWrapper(String error, String message, List<GenericErrorDetail> details) {
        this.error = (error == null)? null : error.toLowerCase();
        this.message = (message == null)? null : message.toLowerCase();
        this.details = details;
    }

    public String getError() {
        return error;
    }
    public void setError(String type) {
        this.error = type;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<GenericErrorDetail> getDetails() {
        return details;
    }
    public void setDetails(List<GenericErrorDetail> details) {
        this.details = details;
    }

    public void addDetail(GenericErrorDetail detail) {
        this.details.add(detail);
    }

    @Override
    public String toString() {
        return "ErrorWrapper{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", details=" + details +
                '}';
    }
}
