package dao;


import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;

public class ConnectActiveMq {
	
	private static ConnectActiveMq instance;
	
	private Connection connection;
	
	private ConnectActiveMq(){}

	public DataSource pool() {
		
		return null;
	}

	public Connection connect() {
		
		if(connection == null )
		{
		
	        String user = "admin";
	        String password =  "admin";
	        String host = "localhost";
	        int port = 61616;
	        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);
	        
	        try {
				connection = factory.createConnection(user, password);
			} catch (JMSException e) {
				
				e.printStackTrace();
			}

	       return connection;
		}
		return connection;
	}

	public void disconnect() {
		try {
			connection.close();
		} catch (JMSException e) {
			
			e.printStackTrace();
		}
	}

	public static ConnectActiveMq getInstance() {
		if(instance == null) {
			instance = new ConnectActiveMq();
		}
		return instance;
	}

}
