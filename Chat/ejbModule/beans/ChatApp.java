package beans;

import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import exceptions.AliasExistsException;

/**
 * Message-Driven Bean implementation class for: ChatApp
 *//*
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		})*//*
@Singleton
@Stateful
@ServerEndpoint(value="/socket")*/
public class ChatApp {

    /**
     * Default constructor. 
     */
    public ChatApp() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    @OnMessage
    public void onMessage(String message,Session session) {
        // TODO Auto-generated method stub
        
    }
    @OnOpen
    public void onOpen(Session session) {
    	System.out.println("session open");
    }
    public List<Host> register(String address, String alias) throws AliasExistsException {
		return null;
	}

    public void unregister(Host host) {
	}
    public void addUser(User user) {
	}

    public void removeUser(User user) {
	}
    public void publish(Message message) {
	}
}
