package dao;


import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;

import javax.sql.DataSource;

public class ConnectActiveMq {
	
	private static ConnectActiveMq instance;
	
	private Connection connection;
	
	private ConnectActiveMq(){}

	public DataSource pool() {
		// TODO Auto-generated method stub
		return null;
	}

	public Connection connect() {
		// TODO Auto-generated method stub
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
				// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
