package beans;

import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import exceptions.AliasExistsException;

/**
 * Message-Driven Bean implementation class for: ChatApp
 *//*
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		})*/
public class ChatApp implements MessageListener {

    /**
     * Default constructor. 
     */
    public ChatApp() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        
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
