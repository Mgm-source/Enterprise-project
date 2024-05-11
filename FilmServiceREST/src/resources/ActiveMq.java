package resources;

import org.apache.activemq.command.ActiveMQTopic;

import dao.ConnectActiveMq;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/topic")
public class ActiveMq {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getAll() {
		
		try {
			ConnectActiveMq.getInstance().connect().start();
			Session sess = ConnectActiveMq.getInstance().connect().createSession(false, Session.AUTO_ACKNOWLEDGE);
	        Destination dest = new ActiveMQTopic("event");

	        MessageConsumer consumer = sess.createConsumer(dest);
	        
	        Message msg = consumer.receive();
	        
	        if( msg instanceof  TextMessage textMessage ) {
                String body = textMessage.getText();
				sess.close();
	        	return Response.ok().entity(body).build();
	        }
	        
				
		} catch (JMSException e) {
			
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
