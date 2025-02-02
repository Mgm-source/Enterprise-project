package com.enterpriseproject.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.mariadb.jdbc.MariaDbPoolDataSource;

public class connectMariaServer implements ConnectionFactory{

	private Connection connection;
	private DataSource pool;
	
	// constructor singleton No new Class
	public connectMariaServer(){}
	
    @Override
	public DataSource pool() {
		if(pool == null) {

			try {
			MariaDbPoolDataSource dataSource = new MariaDbPoolDataSource();

			//dataSource.setServerName("localhost");
			//dataSource.setDatabaseName("FilmsDb");
			dataSource.setUser("root");
			dataSource.setPassword("shemuna2008");
			dataSource.setUrl("jdbc:mariadb://localhost/FilmsDB");

			pool = dataSource;
			} catch (SQLException SQE) {
				throw new RuntimeException("Failed creating pool",SQE.fillInStackTrace());
			}
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
	
