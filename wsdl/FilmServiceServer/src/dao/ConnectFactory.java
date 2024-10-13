package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectFactory {
	// Connection pooling properties 
	private static final int MAX_LIFE = 1800000;
	private static final int IDLE = 600000;
	private static final int TIMEOUT_CON = 10000;
	private static final int MINI_IDLE_CONNECTION = 5;
	private static final int MAX_POOL = 25;
	
	// Database connection properties
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static final String DB_NAME = "mmuassign";
	
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
			
			// configuration object
			HikariConfig config = new HikariConfig();
			
			config.setJdbcUrl(String.format("jdbc:mysql://35.184.249.71:3306/%s", DB_NAME));
			config.setUsername(USER);
			config.setPassword(PASSWORD);
			
			// limits the total number of on going connections to the pool
			config.setMaximumPoolSize(MAX_POOL);
			
			// limits number of idle connections
			config.setMinimumIdle(MINI_IDLE_CONNECTION);
			
			// maximum number of ms to wait for a connection to be given the boot.
			config.setConnectionTimeout(TIMEOUT_CON);
			
			//  maximum amount of time a connection can loiter in the pool / be in the pool for
			config.setIdleTimeout(IDLE);
			
			// when to restart a connection 
			// any connection longer than the life gets rebooted
			config.setMaxLifetime(MAX_LIFE);
			config.setDriverClassName("com.mysql.cj.jdbc.Driver");
			
			// inint the connection pool configuration object
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

