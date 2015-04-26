package org.test.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.test.utils.ApplicationStringsTest.LOGGER_END;
import static org.test.utils.ApplicationStringsTest.LOGGER_START;

public class LoginTest {

    private static Log logger = LogFactory.getLog(LoginTest.class);

    @Test
    public void testEquals() throws ParseException {
        logger.info(LOGGER_START+"testEquals()");
        Login l1 = new Login("uno@email.com", "secreto");
        logger.info(l1);
        Login l2 = new Login("uno@email.com", "secreto");
        logger.info(l2);
        assertTrue(l1.equals(l2));
        l1.setEmail("changed@email.com");
        logger.info(l1);
        assertFalse(l1.equals(l2));
        logger.info(LOGGER_END);
    }
}