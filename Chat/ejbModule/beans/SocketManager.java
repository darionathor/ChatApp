package beans;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

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
        // TODO Auto-generated method stub
    	System.out.println(message);
        
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
