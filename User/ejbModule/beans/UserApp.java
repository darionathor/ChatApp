package beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import exceptions.InvalidCredentialsException;
import exceptions.UsernameExistsException;

/**
 * Message-Driven Bean implementation class for: User
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
	    @ActivationConfigProperty(
	    		propertyName  = "destination",
	    			propertyValue = "queue/mojQueue") // Ext. JNDI Name
		})
public class UserApp implements MessageListener {
	private ArrayList<User> registeredUsers;
	private ArrayList<User> loggedUsers;
    /**
     * Default constructor. 
     */
    public UserApp() {
        // TODO Auto-generated constructor stub
    	registeredUsers=new ArrayList<User>();
    	loggedUsers=new ArrayList<User>();
    	/*try {
			Context context = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) context
					.lookup("RemoteConnectionFactory");
			final Queue queue = (Queue) context
					.lookup("queue/mojQueue");
			context.close();
			Connection connection = cf.createConnection("guest", "guestguest");
			final Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			connection.start();

			MessageConsumer consumer = session.createConsumer(queue);
			consumer.setMessageListener(this);

		    
			consumer.close();
			connection.stop();
		    
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
    	try {
    		ObjectMessage tmsg = (ObjectMessage) message;
    		try {
    			MessageClient mess= (MessageClient) tmsg.getObject();
    			System.out.println(mess.getType()+" UserApp");
    			if(mess.getType().equals("register")){
    				register(mess.getUsername(),mess.getPassword());
    			}else if(mess.getType().equals("login")){
    				login(mess.getUsername(),mess.getPassword());
    			}else if(mess.getType().equals("logout")){
    				for(User user:registeredUsers){
    					if(user.getUsername().equals(mess.getUsername()) && user.getPassword().equals(mess.getPassword())){
    						logout(user);
    						break;
    					}
    				}
    			}
    			
    			
    		} catch (JMSException e) {
    			e.printStackTrace();
    		}
    		} catch (Exception e) {
    			e.printStackTrace ();
    		}
        
    }
    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public User register(String username, String password) throws UsernameExistsException {
    	for(User us: registeredUsers){
			if(us.getUsername().equals(username)){
				throw new UsernameExistsException();
			}
		}
    	User user= new User();
    	user.setPassword(password);
    	user.setUsername(username);
    	user.setHost(null);
    	registeredUsers.add(user);
    	return user;
	}

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean login(String username, String password) throws InvalidCredentialsException {
    	for(User us: registeredUsers){
			if(us.getUsername().equals(username) && us.getPassword().equals(password)){
				loggedUsers.remove(us);
				loggedUsers.add(us);
				
				Context context;
				try {
					context = new InitialContext();
				
	    		ConnectionFactory cf = (ConnectionFactory)
	    				context.lookup("RemoteConnectionFactory");
	    		final Topic queue = (Topic) context
	    				.lookup("topic/mojTopic");

	    		context.close();
	    		Connection connection =
	    		cf.createConnection("guest", "");
	    		final javax.jms.Session session1 =
	    		connection.createSession(false,
	    		javax.jms.Session.AUTO_ACKNOWLEDGE);
	    		connection.start();
	    		MessageClient mess= new MessageClient(us.getUsername(),us.getPassword(),"login","","");
						/*MessageConsumer consumer =
	    				session1.createConsumer(queue);
	    				consumer.setMessageListener(this);*/
	    				ObjectMessage msg = session1.createObjectMessage(mess);

	    				MessageProducer producer =
	    				session1.createProducer(queue);
	    				producer.send(msg);
	    				producer.close();
	    				connection.stop();
	    				connection.close();
	    		} catch (Exception e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    			}
				
				return true;
			}
		}
    	return false;
	}

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean logout(User logout) {
    	for(User us: registeredUsers){
			if(us.getUsername().equals(logout.getUsername()) && us.getPassword().equals(logout.getPassword())){
				us.setHost(null);
				loggedUsers.remove(us);
				Context context;
				try {
					context = new InitialContext();
				
	    		ConnectionFactory cf = (ConnectionFactory)
	    				context.lookup("RemoteConnectionFactory");
	    		final Topic queue = (Topic) context
	    				.lookup("topic/mojTopic");

	    		context.close();
	    		Connection connection =
	    		cf.createConnection("guest", "");
	    		final javax.jms.Session session1 =
	    		connection.createSession(false,
	    		javax.jms.Session.AUTO_ACKNOWLEDGE);
	    		connection.start();
	    		MessageClient mess= new MessageClient(us.getUsername(),us.getPassword(),"logout","","");
						/*MessageConsumer consumer =
	    				session1.createConsumer(queue);
	    				consumer.setMessageListener(this);*/
	    				ObjectMessage msg = session1.createObjectMessage(mess);

	    				MessageProducer producer =
	    				session1.createProducer(queue);
	    				producer.send(msg);
	    				producer.close();
	    				connection.stop();
	    				connection.close();
	    		} catch (Exception e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    			}
				
				
				return true;
			}
		}
		return false;
	}

    @GET
    @Path("/registeredUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
		return registeredUsers;
	}
}
