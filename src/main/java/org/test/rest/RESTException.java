package org.test.rest;

import org.test.error.beans.GenericErrorDetail;
import org.test.utils.ApplicationException;

import java.util.List;

public class RESTException extends ApplicationException {
    public RESTException() {
        super();
    }
    public RESTException(final String type, final String message) {
        super(type, message);
    }
    public RESTException(final String type, final String message, final Throwable cause) {
        super(type, message, cause);
    }
    public RESTException(final String type, final String message, List<GenericErrorDetail> details) {
        super(type, message, details);
    }
    public RESTException(final String type, final String message, List<GenericErrorDetail> details, final Throwable cause) {
        super(type, message, details, cause);
    }
}
