package beans;

import java.io.IOException;
import java.util.HashMap;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Session Bean implementation class SocketManager
 */
@Singleton
@LocalBean
@ServerEndpoint(value="/socket")
public class SocketManager {

	HashMap<Session,User> map;
	HashMap<Session,User> pending;
    /**
     * Default constructor. 
     */
    public SocketManager() {
    		map=new HashMap<Session, User>();
    		pending=new HashMap<Session, User>();
        // TODO Auto-generated constructor stub
    }
    @OnMessage
    public void onMessage(String message,Session session) {
    	System.out.println(message);
    	ObjectMapper mapper = new ObjectMapper();
    	MessageClient mess=null;
    	try {
			mess=mapper.readValue(message, MessageClient.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	/*
        // TODO Auto-generated method stub
    	final Global global = Context.getGlobal();
    	JSONParser parser = new JSONParser(message,global,true);
    	Message mess=(Message) parser.parse();
    	String username = obj.getString("username");
    	String password = obj.getString("password");
    	String type = obj.getString("type");
    	String recipient = obj.getString("recipient");
    	System.out.println(username);
    	System.out.println(password);
    	System.out.println(type);
    	System.out.println(recipient);*/

    	//System.out.println(mess);
    	System.out.println(mess.getType());
    	if(mess.getType().equals("message")){
    		publish(mess);
    	}else if(mess.getType().equals("login")){
    		User us= new User();
    		us.setUsername(mess.getUsername());
    		us.setPassword(mess.getPassword());
    		pending.put(session, us);
    		Context context;
			try {
				context = new InitialContext();
			
    		ConnectionFactory cf = (ConnectionFactory)
    				context.lookup("RemoteConnectionFactory");
    		final Queue queue = (Queue) context
    				.lookup("queue/mojQueue");

    		context.close();
    		Connection connection =
    		cf.createConnection("guest", "");
    		final javax.jms.Session session1 =
    		connection.createSession(false,
    		javax.jms.Session.AUTO_ACKNOWLEDGE);
    		connection.start();
    		/*MessageConsumer consumer =
    				session1.createConsumer(queue);
    				consumer.setMessageListener(this);*/
    				ObjectMessage msg = session1.createObjectMessage(mess);

    				long sent = System.currentTimeMillis();

    				msg.setLongProperty("sent", sent);

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
    	}else if(mess.getType().equals("logout")){
    		Context context;
    		
			try {
				context = new InitialContext();
			
    		ConnectionFactory cf = (ConnectionFactory)
    				context.lookup("RemoteConnectionFactory");
    		final Queue queue = (Queue) context
    				.lookup("queue/mojQueue");

    		context.close();
    		Connection connection =
    		cf.createConnection("guest", "");
    		final javax.jms.Session session1 =
    		connection.createSession(false,
    		javax.jms.Session.AUTO_ACKNOWLEDGE);
    		connection.start();
    		/*MessageConsumer consumer =
    				session1.createConsumer(queue);
    				consumer.setMessageListener(this);*/
    				ObjectMessage msg = session1.createObjectMessage(mess);

    				long sent = System.currentTimeMillis();

    				msg.setLongProperty("sent", sent);

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
    	}else if(mess.getType().equals("register")){
    		Context context;
			try {
				context = new InitialContext();
			
    		ConnectionFactory cf = (ConnectionFactory)
    				context.lookup("RemoteConnectionFactory");
    		final Queue queue = (Queue) context
    				.lookup("queue/mojQueue");

    		context.close();
    		Connection connection =
    		cf.createConnection("guest", "");
    		final javax.jms.Session session1 =
    		connection.createSession(false,
    		javax.jms.Session.AUTO_ACKNOWLEDGE);
    		connection.start();
    		/*MessageConsumer consumer =
    				session1.createConsumer(queue);
    				consumer.setMessageListener(this);*/
    				ObjectMessage msg = session1.createObjectMessage(mess);

    				long sent = System.currentTimeMillis();

    				msg.setLongProperty("sent", sent);

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
    	}
    }
    private void publish(MessageClient mess) {
		// TODO Auto-generated method stub
    	if(mess.getRecipient().equals("")){
			ObjectMapper om= new ObjectMapper();
			String exit="";
			try {
				exit = om.writeValueAsString(mess);
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(Session s:map.keySet()){
				if(!map.get(s).getUsername().equals(mess.getUsername()))
					try {
						s.getBasicRemote().sendText(exit);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}else{
			ObjectMapper om= new ObjectMapper();
			String exit="";
			try {
				exit = om.writeValueAsString(mess);
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(Session s:map.keySet()){
				if(map.get(s).getUsername().equals(mess.getRecipient()))
					try {
						s.getBasicRemote().sendText(exit);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
	@OnOpen
    public void onOpen(Session session) {
    	System.out.println("session open");
    }
	@OnClose
	public void close(Session session, CloseReason c) {
		if(map.containsKey(session)){
		Context context;
		
		try {
			context = new InitialContext();
		
		ConnectionFactory cf = (ConnectionFactory)
				context.lookup("RemoteConnectionFactory");
		final Queue queue = (Queue) context
				.lookup("queue/mojQueue");

		context.close();
		Connection connection =
		cf.createConnection("guest", "");
		final javax.jms.Session session1 =
		connection.createSession(false,
		javax.jms.Session.AUTO_ACKNOWLEDGE);
		connection.start();
		/*MessageConsumer consumer =
				session1.createConsumer(queue);
				consumer.setMessageListener(this);*/
		User user=map.get(session);
			MessageClient mess= new MessageClient(user.getUsername(),user.getPassword(),"logout", "", "");
				ObjectMessage msg = session1.createObjectMessage(mess);

				long sent = System.currentTimeMillis();

				msg.setLongProperty("sent", sent);

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
		}
	}
	public void addUser(User user){
		MessageClient mc= new MessageClient(user.getUsername(),"","newUser","","");
		ObjectMapper om= new ObjectMapper();
		String exit="";
		try {
			exit = om.writeValueAsString(mc);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(Session s:pending.keySet()){
			if(pending.get(s).getUsername().equals(user.getUsername())){
				map.put(s, user);
				pending.remove(s);
				break;
			}
		}
		for(Session s:map.keySet()){
			if(!map.get(s).getUsername().equals(user.getUsername()))
				try {
					s.getBasicRemote().sendText(exit);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("user dodat");
	}
	public void removeUser(User user) {
		// TODO Auto-generated method stub
		MessageClient mc= new MessageClient(user.getUsername(),"","lostUser","","");
		ObjectMapper om= new ObjectMapper();
		String exit="";
		try {
			exit = om.writeValueAsString(mc);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(Session s:map.keySet()){
			if(map.get(s).getUsername().equals(user.getUsername())){
				map.remove(s);
				break;
			}
		}
		for(Session s:map.keySet()){
			if(!map.get(s).getUsername().equals(user.getUsername()))
				try {
					s.getBasicRemote().sendText(exit);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("user uklonjen");
	}
	

}
