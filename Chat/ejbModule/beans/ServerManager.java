package beans;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Message;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBContext;

import exceptions.AliasExistsException;

/**
 * Session Bean implementation class ServerManager
 */
@Startup
@Singleton
public class ServerManager {

	boolean mainServer;
    /**
     * Default constructor. 
     */
	@PostConstruct
    public void initialize() {
    }
    @PreDestroy
    public void terminate() {
    }
    public ServerManager() {
        // TODO Auto-generated constructor stub
    	
    	MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
  		ObjectName socketBindingMBean;
 			try {
 				socketBindingMBean = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");

 	    		String  host = (String)  mBeanServer.getAttribute(socketBindingMBean, "boundAddress");
 	    		Integer port = (Integer) mBeanServer.getAttribute(socketBindingMBean, "boundPort");
 	    		System.out.println(host);
 	    		System.out.println(port);
 	    		if(!port.equals(new Integer(8080))){
 	    			mainServer=false;
 	    			System.out.println("secondary server");
 	    			String uri =
 	    				    "http://localhost:8080/Chat/rest/register/"+host+"/"+port;
 	    				URL url = new URL(uri);
 	    				HttpURLConnection connection =
 	    				    (HttpURLConnection) url.openConnection();
 	    				connection.setRequestMethod("GET");
 	    				connection.setRequestProperty("Accept", "application/xml");
 	    				

 	    				JAXBContext jc = JAXBContext.newInstance(ArrayList.class);
 	    				InputStream xml = connection.getInputStream();
 	    				ArrayList<Host> customer =
 	    				    (ArrayList<Host>) jc.createUnmarshaller().unmarshal(xml);

 	    				connection.disconnect();
 	    		}
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 
    }

}
