package com.enterpriseproject.dao;

import java.sql.Connection;

import javax.sql.DataSource;;

public interface ConnectionFactory {
	// Connection pooling properties
	public DataSource pool();
	
	public Connection connect();
	
	public void disconnect();

	public ConnectionFactory getInstance();
}
