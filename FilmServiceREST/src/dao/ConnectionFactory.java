package dao;

import java.sql.Connection;

import javax.sql.DataSource;;

public interface ConnectionFactory {
	// Connection pooling properties
	// initalizing datasource and connection objects 
	Connection connection = null;
	DataSource pool = null;
	
	public DataSource pool();
	
	public Connection connect();
	
	public void disconnect();
}
