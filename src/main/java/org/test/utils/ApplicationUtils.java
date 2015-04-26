package org.test.utils;

import org.apache.commons.codec.binary.Base64;
import org.test.error.beans.ErrorDetailService;
import org.test.error.beans.GenericErrorDetail;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationUtils {

    public static String formatOutPrintCollection(List<?> collection) {
        String format = "\n\t\t\t\t\t\t\t[\n";
        for(Object object : collection) {
            format += "\t\t\t\t\t\t\t\t"+object.toString()+"\n";
        }
        format += "\t\t\t\t\t\t\t]";
        return format;
    }

    public static List<GenericErrorDetail> getConstraints(ConstraintViolationException e) {
        List<GenericErrorDetail> constraints = new ArrayList<>();
        for(ConstraintViolation<?> constraint : e.getConstraintViolations()) {
            constraints.add(new ErrorDetailService(constraint.getRootBeanClass().toString(), constraint.getPropertyPath().toString(), constraint.getMessage()));
        }
        return constraints;
    }

    public static String getPasswordToStore(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        return Base64.encodeBase64String(md.digest());
    }

}
