package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.mariadb.jdbc.MariaDbPoolDataSource;

public class connectMariaServer implements ConnectionFactory{

	private Connection connection;
	private DataSource pool;
	private static connectMariaServer instance;
	
	// constructor singleton No new Class
	private connectMariaServer(){}
	
	/**
	 * Gets an instance of the connection or creates one if one does not already exist
	 * 
	 * @return a new instance if one does not already exist.
	 */
	public static connectMariaServer getInstance() {
		if(instance == null) {
			instance = new connectMariaServer();
		}
		return instance;
	}
	
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
	
