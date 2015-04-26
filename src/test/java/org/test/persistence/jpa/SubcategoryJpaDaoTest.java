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
import org.test.model.Subcategory;
import org.test.utils.ApplicationUtils;
import org.test.utils.EmfSingleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintViolationException;
import java.sql.DriverManager;
import java.util.List;

import static org.test.utils.ApplicationStringsTest.*;

public class SubcategoryJpaDaoTest {

    private static EntityManagerFactory emf;
    private static IDatabaseConnection connection;
    private static IDataSet dataset;
    private static Log logger = LogFactory.getLog(SubcategoryJpaDaoTest.class);

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
            Category category = categoryJpaDao.find(1L);
            SubcategoryJpaDao subcategoryJpaDao = new SubcategoryJpaDao(em);
            Subcategory subcategory = new Subcategory(null, category);
            subcategoryJpaDao.create(subcategory);
        } catch(ConstraintViolationException e) {
            for(GenericErrorDetail c : ApplicationUtils.getConstraints(e)) logger.info(c.toString());
            logger.info(LOGGER_END);
            throw e;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void nameMaxSizeTest() {
        logger.info(LOGGER_START+"nameMaxSizeTest()");
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            CategoryJpaDao categoryJpaDao = new CategoryJpaDao(em);
            Category category = categoryJpaDao.find(1L);
            SubcategoryJpaDao subcategoryJpaDao = new SubcategoryJpaDao(em);
            Subcategory subcategory = new Subcategory(STRING_LARGER_THAN_100, category);
            subcategoryJpaDao.create(subcategory);
        } catch(ConstraintViolationException e) {
            for(GenericErrorDetail c : ApplicationUtils.getConstraints(e)) logger.info(c.toString());
            logger.info(LOGGER_END);
            throw e;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void categoryNullTest() {
        logger.info(LOGGER_START+"categoryNullTest()");
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            SubcategoryJpaDao subcategoryJpaDao = new SubcategoryJpaDao(em);
            Subcategory subcategory = new Subcategory("prueba", null);
            subcategoryJpaDao.create(subcategory);
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
        Category category = categoryJpaDao.find(1L);
		SubcategoryJpaDao subcategoryJpaDao = new SubcategoryJpaDao(em);
		Subcategory subcategory = new Subcategory("prueba", category);
		subcategoryJpaDao.create(subcategory);
        Long idSubcategory = subcategory.getId();
		Assert.assertNotNull(idSubcategory);
		em.getTransaction().commit();
		em.close();
		em = emf.createEntityManager();
		em.getTransaction().begin();
		subcategoryJpaDao = new SubcategoryJpaDao(em);
		Subcategory subcategory2 = subcategoryJpaDao.find(idSubcategory);
		Assert.assertNotNull(subcategory2);
		Assert.assertTrue(subcategory2.getName().equals(subcategory.getName()));
		em.getTransaction().commit();
		em.close();
        logger.info(subcategory);
        logger.info(subcategory2);
        logger.info(LOGGER_END);
	}

    @Test
    public void updateTest() {
        logger.info(LOGGER_START+"updateTest()");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        SubcategoryJpaDao subcategoryJpaDao = new SubcategoryJpaDao(em);
        Subcategory subcategory = subcategoryJpaDao.find(1L);
        logger.info("Subcategory before update...");
        logger.info(subcategory);
        subcategory.setName("@@CHANGED@@");
        subcategoryJpaDao.update(subcategory);
        em.getTransaction().commit();
        em.close();
        em = emf.createEntityManager();
        em.getTransaction().begin();
        subcategoryJpaDao = new SubcategoryJpaDao(em);
        subcategory = subcategoryJpaDao.find(1L);
        logger.info("Subcategory after update...");
        logger.info(subcategory);
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
		Subcategory subcategory = subcategoryJpaDao.find(39L);
		logger.info("Subcategory to delete...");
        logger.info(subcategory);
		subcategoryJpaDao.delete(subcategory);
		em.getTransaction().commit();
		em.close();
		em = emf.createEntityManager();
		em.getTransaction().begin();
		subcategoryJpaDao = new SubcategoryJpaDao(em);
		subcategory = subcategoryJpaDao.find(39L);
		Assert.assertTrue(subcategory == null);
		List<Subcategory> subcategories = subcategoryJpaDao.findAllOrderByName();
		em.getTransaction().commit();
		em.close();
		logger.info("Now...");
        logger.info(ApplicationUtils.formatOutPrintCollection(subcategories));
        logger.info(LOGGER_END);
	}

    @Test
    public void findTest() {
        logger.info(LOGGER_START+"findTest()");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        SubcategoryJpaDao subcategoryJpaDao = new SubcategoryJpaDao(em);
        Subcategory subcategory = subcategoryJpaDao.find(1L);
        Assert.assertNotNull(subcategory);
        Assert.assertTrue(subcategory.getName().equals("PRUEBAS HEPÁTICAS"));
        em.getTransaction().commit();
        em.close();
        logger.info(subcategory);
        logger.info(LOGGER_END);
    }

    @Test
    public void existsNaturalKeyTest() {
        logger.info(LOGGER_START+"isRepeatedNaturalKeyTest()");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CategoryJpaDao categoryJpaDao = new CategoryJpaDao(em);
        Category category = categoryJpaDao.find(1L);
        SubcategoryJpaDao subcategoryJpaDao = new SubcategoryJpaDao(em);
        logger.info("Checking \"IONES\"...");
        Assert.assertTrue(subcategoryJpaDao.existsNaturalKey("IONES", category));
        logger.info("Checking \"ION\"...");
        Assert.assertFalse(subcategoryJpaDao.existsNaturalKey("ION", category));
        em.getTransaction().commit();
        em.close();
        logger.info(LOGGER_END);
    }

	@Test
	public void findAllAscTest() {
        logger.info(LOGGER_START+"findAllAscTest()");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		SubcategoryJpaDao subcategoryJpaDao = new SubcategoryJpaDao(em);
		List<Subcategory> subcategories = subcategoryJpaDao.findAllOrderByName();
		Assert.assertTrue(subcategories.size() == 43);
		Assert.assertEquals(subcategories.get(2).getName(),"CARIOTIPOS");
		em.getTransaction().commit();
		em.close();
        logger.info(ApplicationUtils.formatOutPrintCollection(subcategories));
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
