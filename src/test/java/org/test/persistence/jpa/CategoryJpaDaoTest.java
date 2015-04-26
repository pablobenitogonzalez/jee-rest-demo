package org.test.persistence.jpa;


import com.mysql.jdbc.Connection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;
import org.test.error.beans.GenericErrorDetail;
import org.test.model.Category;
import org.test.model.Record;
import org.test.model.Subcategory;
import org.test.utils.ApplicationUtils;
import org.test.utils.EmfSingleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintViolationException;
import java.sql.DriverManager;
import java.util.List;

import static org.test.utils.ApplicationStringsTest.*;

public class CategoryJpaDaoTest {

    private static EntityManagerFactory emf;
    private static IDatabaseConnection connection;
    private static IDataSet dataset;
    private static Log logger = LogFactory.getLog(CategoryJpaDaoTest.class);

    @BeforeClass
    public static void initDatabaseTest() {
        try {
            logger.info(LOGGER_INIT_DBUNIT_BUILDING);
            emf = EmfSingleton.getInstance().getEmf();
            Class.forName(DBUNIT_CONFIG_DRIVER);
            Connection jdbcConnection = (Connection) DriverManager.getConnection(DBUNIT_CONFIG_CONNECTION_URL, DBUNIT_CONFIG_CONNECTION_USER, DBUNIT_CONFIG_CONNECTION_PASSWORD);
            connection = new DatabaseConnection(jdbcConnection);
            DatabaseConfig dbConfig = connection.getConfig();
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
            FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
            flatXmlDataSetBuilder.setColumnSensing(true);
            dataset = flatXmlDataSetBuilder.build(Thread.currentThread().getContextClassLoader().getResourceAsStream(DBUNIT_CONFIG_PATH_DATASET));
        } catch (Exception e) {
            logger.error(LOGGER_EXCEPTION_DBUNIT_BUILDING);
            e.printStackTrace();
        }
    }

    @Before
    public void cleanDB() throws Exception {
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
    }

    @Test(expected = ConstraintViolationException.class)
    public void nameNullTest() {
        logger.info(LOGGER_START+"nameNullTest()");
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            CategoryJpaDao categoryJpaDao = new CategoryJpaDao(em);
            Category category = new Category(null);
            categoryJpaDao.create(category);
        } catch(ConstraintViolationException e) {
            for(GenericErrorDetail c : ApplicationUtils.getConstraints(e)) logger.info(c.toString());
            logger.info(LOGGER_END);
            throw e;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void nameMinSizeTest() {
        logger.info(LOGGER_START+"nameMinSizeTest()");
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            CategoryJpaDao categoryJpaDao = new CategoryJpaDao(em);
            Category category = new Category("");
            categoryJpaDao.create(category);
        } catch(ConstraintViolationException e) {
            for(GenericErrorDetail c : ApplicationUtils.getConstraints(e)) logger.info(c.toString());
            logger.info(LOGGER_END);
            throw e;
        }
    }

    @Test
	public void createTest() {
        logger.info(LOGGER_START+"createTest()");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		CategoryJpaDao categoryJpaDao = new CategoryJpaDao(em);
		Category category = new Category("prueba");
		categoryJpaDao.create(category);
		Assert.assertNotNull(category.getId());
		Long idCategory = category.getId();
		em.getTransaction().commit();
		em.close();
		em = emf.createEntityManager();
		em.getTransaction().begin();
		categoryJpaDao = new CategoryJpaDao(em);
		Category category2 = categoryJpaDao.find(idCategory);
		Assert.assertNotNull(category2);
		Assert.assertTrue(category2.getName().equals(category.getName()));
		em.getTransaction().commit();
		em.close();
        logger.info(category);
        logger.info(category2);
        logger.info(LOGGER_END);
	}

    @Test
    public void updateTest() {
        logger.info(LOGGER_START+"updateTest()");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CategoryJpaDao categoryJpaDao = new CategoryJpaDao(em);
        Category category = categoryJpaDao.find(1L);
        logger.info("Category before update...");
        logger.info(category);
        category.setName("@@CHANGED@@");
        Record record = new Record();
        category.getRecord().setLastUpdate(record.getLastUpdate());
        categoryJpaDao.update(category);
        em.getTransaction().commit();
        em.close();
        em = emf.createEntityManager();
        em.getTransaction().begin();
        categoryJpaDao = new CategoryJpaDao(em);
        category = categoryJpaDao.find(1L);
        logger.info("Category after update...");
        logger.info(category);
        em.getTransaction().commit();
        em.close();
        logger.info(LOGGER_END);
    }

	@Test
	public void deleteTest() {
        logger.info(LOGGER_START+"deleteTest()");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		SubcategoryJpaDao subcategoryJpaDao = new SubcategoryJpaDao(em);
		for(long i = 35; i <= 39; i++) {
			subcategoryJpaDao.delete(subcategoryJpaDao.find(i));
		}
		List<Subcategory> subcategories = subcategoryJpaDao.findAllOrderByName();
		em.getTransaction().commit();
		em.close();
        logger.info(ApplicationUtils.formatOutPrintCollection(subcategories));
		em = emf.createEntityManager();
		em.getTransaction().begin();
		CategoryJpaDao categoryJpaDao = new CategoryJpaDao(em);
		Category category = categoryJpaDao.find(10L);
		logger.info("Category to delete...");
        logger.info(category);
		categoryJpaDao.delete(category);
		em.getTransaction().commit();
		em.close();
		em = emf.createEntityManager();
		em.getTransaction().begin();
		categoryJpaDao = new CategoryJpaDao(em);
		category = categoryJpaDao.find(10L);
		Assert.assertTrue(category == null);
		List<Category> categories = categoryJpaDao.findAllOrderByName();
		em.getTransaction().commit();
		em.close();
        logger.info("Now...");
        logger.info(ApplicationUtils.formatOutPrintCollection(categories));
        logger.info(LOGGER_END);
	}

    @Test
    public void findTest() {
        logger.info(LOGGER_START+"findTest()");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CategoryJpaDao categoryJpaDao = new CategoryJpaDao(em);
        Category category = categoryJpaDao.find(1L);
        Assert.assertNotNull(category);
        Assert.assertTrue(category.getName().equals("BIOQUÍMICA"));
        em.getTransaction().commit();
        em.close();
        logger.info(category);
        logger.info(LOGGER_END);
    }

    @Test
    public void existsNaturalKeyTest() {
        logger.info(LOGGER_START+"existsNaturalKey()");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CategoryJpaDao categoryJpaDao = new CategoryJpaDao(em);
        logger.info("Checking \"BIOQUÍMICA\"...");
        Assert.assertTrue(categoryJpaDao.existsNaturalKey("BIOQUÍMICA"));
        logger.info("Checking \"BIO\"...");
        Assert.assertFalse(categoryJpaDao.existsNaturalKey("BIO"));
        em.getTransaction().commit();
        em.close();
        logger.info(LOGGER_END);
    }

	@Test
	public void findAllAscTest() {
        logger.info(LOGGER_START+"findAllAscTest()");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		CategoryJpaDao categoryJpaDao = new CategoryJpaDao(em);
		List<Category> categories = categoryJpaDao.findAllOrderByName();
		Assert.assertTrue(categories.size() == 12);
		Assert.assertEquals(categories.get(2).getName(), "COAGULACIÓN");
        em.getTransaction().commit();
		em.close();
        logger.info(ApplicationUtils.formatOutPrintCollection(categories));
        logger.info(LOGGER_END);
	}

    @AfterClass
    public static void closeEntityManagerFactory() throws Exception {
        DatabaseOperation.DELETE_ALL.execute(connection, dataset);
        logger.info(LOGGER_CLOSING_EMF);
        if (emf != null) {
            emf.close();
        }
    }
}
