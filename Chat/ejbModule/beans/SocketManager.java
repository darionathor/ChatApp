package beans;

import java.io.IOException;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Session Bean implementation class SocketManager
 */
@Singleton
@LocalBean
@ServerEndpoint(value="/socket")
public class SocketManager {

    /**
     * Default constructor. 
     */
    public SocketManager() {
        // TODO Auto-generated constructor stub
    }
    @OnMessage
    public void onMessage(String message,Session session) {
    	System.out.println(message);
    	ObjectMapper mapper = new ObjectMapper();
    	Message mess=null;
    	try {
			mess=mapper.readValue(message, Message.class);
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
    }
    @OnOpen
    public void onOpen(Session session) {
    	System.out.println("session open");
    }
	@OnClose
	public void close(Session session, CloseReason c) {
		System.out.println("session closed");
	}

}
