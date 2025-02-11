package com.enterpriseproject.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

@Configuration
public class ConnectSQLServer implements ConnectionFactory {
	// Connection pooling properties
	// initalizing datasource and connection objects 
	private Connection connection;
	private DataSource pool;
	static private ConnectSQLServer instance;

	@Value("${sqlserver.servername}")
	private String serverName;
	@Value("${sqlserver.databasename}")
	private String databaseName;
	@Value("${sqlserver.trustcert}")
	private Boolean trustServer;
	@Value("${sqlserver.user}")
	private String user;
	@Value("${sqlserver.password}")
	private String password;
	
	// constructor singleton No new Class
	protected  ConnectSQLServer(){}

	@Bean
	public static ConnectSQLServer getInstance()
	{
		if(instance == null)
		{
			instance = new ConnectSQLServer();
		}

		return instance;
	}
	
    @Override
	public DataSource pool() {
		if(pool == null) {

			SQLServerDataSource dataSource = new SQLServerDataSource();

			dataSource.setServerName(serverName);
			dataSource.setDatabaseName(databaseName);
			dataSource.setTrustServerCertificate(trustServer);
			dataSource.setUser(user);
			dataSource.setPassword(password);

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

