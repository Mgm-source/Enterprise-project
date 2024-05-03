package resources;

import org.apache.activemq.command.ActiveMQTopic;

import dao.ConnectActiveMq;
import jakarta.jms.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/topic")
public class ActiveMq {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getAll() {
		
		try {
			ConnectActiveMq.getInstance().connect().start();
			Session sess = ConnectActiveMq.getInstance().connect().createSession(false, Session.AUTO_ACKNOWLEDGE);
	        Destination dest = new ActiveMQTopic("event");

	        MessageConsumer consumer = sess.createConsumer(dest);
	        
	        Message msg = consumer.receive();
	        
	        if( msg instanceof  TextMessage ) {
                String body = ((TextMessage) msg).getText();
	        	return Response.ok().entity(body).build();
	        }
	        
				
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		ConnectActiveMq.getInstance().disconnect();
		return Response.status(404).build();
	}
}
