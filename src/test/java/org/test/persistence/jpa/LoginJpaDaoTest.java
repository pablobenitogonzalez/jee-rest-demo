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
import org.test.model.Login;
import org.test.model.Role;
import org.test.utils.EmfSingleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;

import static org.test.utils.ApplicationStringsTest.*;

public class LoginJpaDaoTest {

    private static EntityManagerFactory emf;
    private static IDatabaseConnection connection;
    private static IDataSet dataset;
    private static Log logger = LogFactory.getLog(LoginJpaDaoTest.class);

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

    @Test
	public void createTest() throws NoSuchAlgorithmException {
        logger.info(LOGGER_START+"createTest()");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		LoginJpaDao loginJpaDao = new LoginJpaDao(em);
		Login login = new Login("email@prueba.com", "1234", Role.USER);
        loginJpaDao.create(login);
		Assert.assertNotNull(login.getId());
		em.getTransaction().commit();
		em.close();
        logger.info(login);
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
