package org.test.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.test.utils.ApplicationStringsTest.LOGGER_END;
import static org.test.utils.ApplicationStringsTest.LOGGER_START;

public class CategoryTest {

    private static Log logger = LogFactory.getLog(CategoryTest.class);

    @Test
    public void testEquals() {
        logger.info(LOGGER_START+"testEquals()");
        Category c1 = new Category("una");
        logger.info(c1);
        Category c2 = new Category("una");
        logger.info(c2);
        assertTrue(c1.equals(c2));
        c2.setName("changed");
        logger.info(c2);
        assertFalse(c1.equals(c2));
        logger.info(LOGGER_END);
    }

	@Test
	public void testContainsSubcategory() {
        logger.info(LOGGER_START+"testContainsSubcategory()");
        Category c = new Category("una");
        logger.info(c);
		Subcategory s1 = new Subcategory("sub-una", c);
        logger.info(s1);
		Subcategory s2 = new Subcategory("sub-dos", c);
        logger.info(s2);
		Subcategory s3 = new Subcategory("sub-tres", c);
        logger.info(s3);
		c.getSubcategories().add(s1);
		c.getSubcategories().add(s2);
		assertTrue(c.getSubcategories().contains(s1));
		assertFalse(c.getSubcategories().contains(s3));
		assertTrue(c.getSubcategories().contains(new Subcategory("sub-una", c)));
        logger.info(LOGGER_END);
	}
}
