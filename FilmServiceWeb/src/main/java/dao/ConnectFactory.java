package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectFactory {
	
	private static final int MAX_LIFE = 1800000;
	private static final int IDLE = 600000;
	private static final int TIMEOUT_CON = 10000;
	private static final int MINI_IDLE_CONNECTION = 5;
	private static final int MAX_POOL = 25;
	// Database constants initializing
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static final String DB_NAME = "mmuassign";
	private static String messageSQL = "Failed to connect to Database";
	private static final String SOCKET = "com.google.cloud.sql.mysql.SocketFactory";
	private static final String GOOGLE_CON_NAME = "testbuildmun1of1:us-central1:filmsonline";
	
	
	// initalizing datasource and connection objects 
	private Connection connection;
	private DataSource pool;
		
	// initalizing the gobal instance of the connection
	private static ConnectFactory instance;
	
	// constructor singleton No new Class
	private ConnectFactory(){}
	
	/**
	 * Gets an instance of the connection or creates one if one does not already exist
	 * 
	 * @return a new instance if one does not already exist.
	 */
	public static ConnectFactory getInstance() {
		if(instance == null) {
			instance = new ConnectFactory();
		}
		return instance;
	}
	
	public DataSource pool() {
		if(pool == null) {
			
			HikariConfig config = new HikariConfig();
			
			config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
			config.setUsername(USER);
			config.setPassword(PASSWORD);
		    config.addDataSourceProperty("socketFactory", SOCKET);
		    config.addDataSourceProperty("cloudSqlInstance", GOOGLE_CON_NAME);
			config.setMaximumPoolSize(MAX_POOL);
			config.setMinimumIdle(MINI_IDLE_CONNECTION);
			config.setConnectionTimeout(TIMEOUT_CON);
			config.setIdleTimeout(IDLE);
			config.setMaxLifetime(MAX_LIFE);
			config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");
			// wondering where i set the driver it's in the appengine xml
			
			pool = new HikariDataSource(config);
		}
		
		return pool;
	}
	
	public Connection connect() {
		if(connection == null) {
			try {
				connection = pool().getConnection();
				
			} catch (SQLException SQE) {
				throw new RuntimeException(messageSQL,SQE.fillInStackTrace());
				
			}
		}
		return connection;
	}
	
	public void disconnect() {
		
		if(connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException SQE) {
				SQE.printStackTrace();
			}
		}
	}
	
}




