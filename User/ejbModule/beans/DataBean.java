package beans;

import java.util.ArrayList;

import javax.ejb.Remote;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class DataBean
 */
@Singleton
@Remote
public class DataBean {
	public ArrayList<User> registeredUsers;
	public ArrayList<User> loggedUsers;
    /**
     * Default constructor. 
     */
    public DataBean() {
        // TODO Auto-generated constructor stub
    	registeredUsers=new ArrayList<User>();loggedUsers=new ArrayList<User>();
    }

}
