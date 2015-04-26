package org.test.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.test.error.beans.ErrorWrapper;
import org.test.error.beans.GenericErrorDetail;
import org.test.utils.ApplicationException;
import org.test.utils.ApplicationUtils;

import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

import static org.test.utils.ApplicationStrings.*;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<RuntimeException> {

    private static Log logger = LogFactory.getLog(ApplicationExceptionMapper.class);

    public Response toResponse(RuntimeException e) {
        String message = (e.getMessage() == null)? MESSAGE_INTERNAL_SERVER : e.getMessage();
        logger.error(message, e);
        // REST - @QueryParam unable to extract parameter
        if(e instanceof NotFoundException) {
            return this.responseJson(Response.Status.BAD_REQUEST, new ErrorWrapper(ERROR_BAD_PARAM, MESSAGE_BAD_PARAM, null));
        }
        // REST - RESTException
        else if(e instanceof RESTException) {
            switch (((RESTException) e).getErrorWrapper().getError()) {
                case ERROR_ROLE_ACCESS :
                    return this.responseJson(Response.Status.UNAUTHORIZED,((RESTException) e).getErrorWrapper());
                case ERROR_LOGIN_ACCESS :
                    return this.responseJson(Response.Status.UNAUTHORIZED,((RESTException) e).getErrorWrapper());
                case ERROR_USER_CONTEXT :
                    return this.responseJson(Response.Status.UNAUTHORIZED,((RESTException) e).getErrorWrapper());
            }
            return null;
        }
        // Service Auth - EJBAccessException
        else if(e instanceof EJBAccessException) {
            return this.responseJson(Response.Status.UNAUTHORIZED, new ErrorWrapper(ERROR_LOGIN_ACCESS, MESSAGE_LOGIN_ACCESS, null));
        }
        // Service Exception - EJBException
        else if (e instanceof EJBException) {
            // ApplicationException
            if(e.getCause() instanceof ApplicationException) {
                switch (((ApplicationException) e.getCause()).getErrorWrapper().getError()) {
                    case ERROR_NOT_FOUND:
                        return this.responseJson(Response.Status.NOT_FOUND, ((ApplicationException) e.getCause()).getErrorWrapper());
                    case ERROR_UNIQUE_KEY:
                        return this.responseJson(Response.Status.BAD_REQUEST, ((ApplicationException) e.getCause()).getErrorWrapper());
                    case ERROR_REFERENTIAL_INTEGRITY:
                        return this.responseJson(Response.Status.BAD_REQUEST, ((ApplicationException) e.getCause()).getErrorWrapper());
                    default:
                        return this.responseJson(Response.Status.BAD_REQUEST, ((ApplicationException) e.getCause()).getErrorWrapper());
                }
            }
            // ConstraintViolationException
            else if (e.getCause() instanceof ConstraintViolationException) {
                List<GenericErrorDetail> details = ApplicationUtils.getConstraints(((ConstraintViolationException) e.getCause()));
                ErrorWrapper errorWrapper = new ErrorWrapper(ERROR_VALIDATION, MESSAGE_VALIDATION, details);
                return this.responseJson(Response.Status.BAD_REQUEST, errorWrapper);
            }
            // Other
            else return responseJson(Response.Status.INTERNAL_SERVER_ERROR, new ErrorWrapper(ERROR_INTERNAL_SERVER, message));
        }
        // Other
        else return responseJson(Response.Status.INTERNAL_SERVER_ERROR, new ErrorWrapper(ERROR_INTERNAL_SERVER, message));
    }

    private Response responseJson(Response.Status status, ErrorWrapper error) {
        return Response.status(status).entity(error).build();
    }
}
