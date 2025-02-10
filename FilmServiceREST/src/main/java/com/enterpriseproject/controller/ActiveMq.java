package com.enterpriseproject.controller;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterpriseproject.dao.ConnectActiveMq;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@RestController
@RequestMapping(value = "Topic")
public class ActiveMq {
	
	@GetMapping(produces = "text/pain")
	public ResponseEntity<String> getAll() {
		
		ConnectActiveMq activeq = new ConnectActiveMq().getActiveMq();

		try {
			activeq.connect().start();
			Session sess = activeq.connect().createSession(false, Session.AUTO_ACKNOWLEDGE);
	        Destination dest = new ActiveMQTopic("event");

	        MessageConsumer consumer = sess.createConsumer(dest);
	        
	        Message msg = consumer.receive();
	        
	        if( msg instanceof  TextMessage textMessage ) {
                String body = textMessage.getText();
				sess.close();
	        	return ResponseEntity.ok().body(body);
	        }
	        
				
		} catch (JMSException e) {
			
			e.printStackTrace();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		activeq.disconnect();
		return ResponseEntity.status(404).build();
	}
}
