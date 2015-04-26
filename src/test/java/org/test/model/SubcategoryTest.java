package org.test.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.test.utils.ApplicationStringsTest.LOGGER_END;
import static org.test.utils.ApplicationStringsTest.LOGGER_START;

public class SubcategoryTest {

    private static Log logger = LogFactory.getLog(SubcategoryTest.class);

    @Test
	public void testEquals() {
        logger.info(LOGGER_START+"testEquals()");
		Category c = new Category("una");
        logger.info(c);
		Subcategory s1 = new Subcategory("sub-una", c);
        logger.info(s1);
		Subcategory s2 = new Subcategory("sub-una", c);
        logger.info(s2);
		assertTrue(s1.equals(s2));
		s2.setName("sub-dos");
        logger.info(s2);
		assertFalse(s1.equals(s2));
        logger.info(LOGGER_END);
	}

	@Test
	public void testSameCategoryIndentificator() {
        logger.info(LOGGER_START+"testSameCategoryIndentificator()");
		Category c1 = new Category("una");
        logger.info(c1);
        Subcategory s1 = new Subcategory("sub-una", c1);
        logger.info(s1);
        Subcategory s2 = new Subcategory("sub-dos", c1);
        logger.info(s2);
		assertTrue(s1.getCategory().equals(s2.getCategory()));
		Category c2 = new Category("dos");
        logger.info(c2);
        Subcategory subc4 = new Subcategory("sub-cuatro", c2);
        subc4.setCategory(c1);
        logger.info(subc4);
		s1.setCategory(c2);
		assertFalse(s1.getCategory().equals(subc4.getCategory()));
        logger.info(LOGGER_END);
	}
}
