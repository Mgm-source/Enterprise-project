package com.enterpriseproject.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class connectSQLServer implements ConnectionFactory{
	// Connection pooling properties
	// initalizing datasource and connection objects 
	private Connection connection;
	private DataSource pool;
		
	// initalizing the gobal instance of the connection
	private static connectSQLServer instance;
	
	// constructor singleton No new Class
	public connectSQLServer(){}
	
	/**
	 * Gets an instance of the connection or creates one if one does not already exist
	 * 
	 * @return a new instance if one does not already exist.
	 */
	@Override
	public  connectSQLServer getInstance() {
		if(instance == null) {
			instance = new connectSQLServer();
		}
		return instance;
	}
	
    @Override
	public DataSource pool() {
		if(pool == null) {

			SQLServerDataSource dataSource = new SQLServerDataSource();

			dataSource.setServerName("localhost");
			dataSource.setDatabaseName("FilmsDb");
			dataSource.setTrustServerCertificate(true);
			dataSource.setUser("filmsDBAdmin");
			dataSource.setPassword("");

			pool = dataSource;
		}
		
		return pool;
	}
	
    @Override
	public Connection connect() {
		if(connection == null) {
			try {
				connection = pool().getConnection();
				
			} catch (SQLException SQE) {
				throw new RuntimeException("Failed connecting to SQL SERVER",SQE.fillInStackTrace());
			}
		}
		return connection;
	}
	
	@Override
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

