package org.test.utils;

import com.mysql.jdbc.Connection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.sql.DriverManager;

public class DataExportForDBUnit {

	private static IDatabaseConnection connection;
	private static Log logger = LogFactory.getLog(DataExportForDBUnit.class);
	private final static String CONNECTION_DB = "jdbc:mysql://localhost:3306/auth";
	private final static String CONNECTION_USER = "root";
	private final static String CONNECTION_PWD = "pablo-1";
	private final static String FILE = "src/test/resources/dbunit/dataset.xml";

	@Ignore
	@Test
	public void getFullDataSet() {
		try {
			logger.info("Extracting full dataset...");
			Class.forName("com.mysql.jdbc.Driver");
			final Connection jdbcConnection = (Connection) DriverManager.getConnection(CONNECTION_DB, CONNECTION_USER, CONNECTION_PWD);
			connection = new DatabaseConnection(jdbcConnection);
			DatabaseConfig dbConfig = connection.getConfig();
			dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
			final IDataSet fullDataSet = connection.createDataSet();
			FlatXmlDataSet.write(fullDataSet, new FileOutputStream(FILE));
			logger.info("Finish");
			Assert.assertTrue(true);
		} catch (final Exception e) {
			logger.error("Exception during data export");
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getFullOrderedDataSet() {
		try {
			logger.info("Extracting full dataset...");
			Class.forName("com.mysql.jdbc.Driver");
			final Connection jdbcConnection = (Connection) DriverManager.getConnection(CONNECTION_DB, CONNECTION_USER, CONNECTION_PWD);
			connection = new DatabaseConnection(jdbcConnection);
			DatabaseConfig dbConfig = connection.getConfig();
			dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
			final QueryDataSet dataSet = new QueryDataSet(connection);
			dataSet.addTable(ApplicationStrings.CATEGORY);
			dataSet.addTable(ApplicationStrings.SUBCATEGORY);
			dataSet.addTable(ApplicationStrings.LOGIN);
			FlatXmlDataSet.write(dataSet, new FileOutputStream(FILE));
			logger.info("Finish");
			Assert.assertTrue(true);
		} catch (final Exception e) {
			logger.error("Exception during data export");
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
}
