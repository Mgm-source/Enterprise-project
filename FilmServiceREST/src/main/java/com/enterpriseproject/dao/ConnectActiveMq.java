package com.enterpriseproject.dao;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;

@Configuration
public class ConnectActiveMq {
	
	private static ConnectActiveMq instance;
	
	private Connection connection;
	
	public ConnectActiveMq(){}
	
	@Value("${activemqueue.user}")
	String user;
	@Value("${activemqueue.password}")
	String password;
	@Value("${activemqueue.host}")
	String host;
	@Value("${activemqueue.port}")
	int port;

	public Connection connect() {
		
		if(connection == null )
		{
		
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

	@Bean
	public ConnectActiveMq getActiveMq() {
		if(instance == null) {
			instance = new ConnectActiveMq();
		}
		return instance;
	}

}
