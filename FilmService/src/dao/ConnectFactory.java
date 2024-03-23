package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class ConnectFactory {
	// Connection pooling properties 
	
	// Database connection properties
	private static final String USER = "filmsDBAdmin";
	private static final String PASSWORD = "";
	private static final String DB_NAME = "FilmsDB";
	
	// Generic error message 
	private static String messageSQL = "Failed to connect to Database";
	
	
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
	
	/**
	 * Gets an instance of the pool or creates one if one does not already exist
	 * 
	 * @return a new instance if one does not already exist.
	 */
	public DataSource pool() {
		if(pool == null) {
			
			// sourceuration object
			SQLServerDataSource source = new SQLServerDataSource();
			
			source.setServerName("localhost");
			source.setDatabaseName(DB_NAME);
			source.setUser(USER);
			source.setPassword(PASSWORD);
			source.setTrustServerCertificate(true);
			
			// inint the connection pool sourceuration object
			pool = source;
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

