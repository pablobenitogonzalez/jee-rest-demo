package org.test.utils;

import org.test.error.beans.ErrorWrapper;
import org.test.error.beans.GenericErrorDetail;

import java.util.List;

public class ApplicationException extends RuntimeException {

	private ErrorWrapper errorWrapper;

	public ApplicationException() {
		super();
		this.errorWrapper = new ErrorWrapper();
	}
	public ApplicationException(final String type, final String message) {
		super(message);
		this.errorWrapper = new ErrorWrapper(type, message);
	}
	public ApplicationException(final String type, final String message, final Throwable cause) {
		super(message, cause);
		this.errorWrapper = new ErrorWrapper(type, message);
	}
	public ApplicationException(final String type, final String message, List<GenericErrorDetail> details) {
		super(message);
		this.errorWrapper = new ErrorWrapper(type, message, details);
	}
	public ApplicationException(final String type, final String message, List<GenericErrorDetail> details, final Throwable cause) {
		super(message, cause);
		this.errorWrapper = new ErrorWrapper(type, message, details);
	}

	public ErrorWrapper getErrorWrapper() {
		return errorWrapper;
	}
	public void setErrorWrapper(ErrorWrapper errorWrapper) {
		this.errorWrapper = errorWrapper;
	}
}
