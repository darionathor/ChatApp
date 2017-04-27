package beans;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.AliasExistsException;

/**
 * Message-Driven Bean implementation class for: ChatApp
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
			    @ActivationConfigProperty(
			    		propertyName  = "destination",
			    			propertyValue = "topic/mojTopic")
		})
public class ChatAplikacija implements MessageListener{

	@Inject
	SocketManager sm;
	@EJB ServerManager data;
    /**
     * Default constructor. 
     */
    public ChatAplikacija() {
        // TODO Auto-generated constructor stub
    	System.out.println("CA started");
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
   
    @OnOpen
    public void onOpen(Session session) {
    	System.out.println("session open");
    }
    public void addUser(User user) {
    	sm.addUser(user);
    	if(data.main){
    		data.addUser(user);
    		
    	}
	}

    public void removeUser(User user) {
    	sm.removeUser(user);
    	if(data.main){
    		data.removeUser(user);
    		
    	}
	}

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		ObjectMessage tmsg = (ObjectMessage) message;
		try {
			MessageClient mess= (MessageClient) tmsg.getObject();
			System.out.println(mess.getType()+" chatApp");
			User user= new User();
			user.setUsername(mess.getUsername());
			user.setPassword(mess.getPassword());
			if(mess.getType().equals("login")){
				addUser(user);
			}else if(mess.getType().equals("logout")){
				removeUser(user);
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
