package beans;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
@Path("/rest/")
public class ServerManager {

	boolean mainServer;
    /**
     * Default constructor. 
     */
	 
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
 	    				

 	    				JAXBContext jc = JAXBContext.newInstance(List.class);
 	    				InputStream xml = connection.getInputStream();
 	    				List<Host> customer =
 	    				    (List<Host>) jc.createUnmarshaller().unmarshal(xml);

 	    				connection.disconnect();
 	    		}
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 
    }
    @GET
    @Path("/register/{param1}/{param2}")
    @Produces({"application/xml","application/json"})
    //@Consumes({"application/xml","application/json"})
    public List<Host> register(@PathParam("param1") String address,@PathParam("param2") String alias) throws AliasExistsException {
		System.out.println(address);
		System.out.println(alias);
    	return new ArrayList<Host>();
	}

    @POST
    @Consumes({"application/xml","application/json"})
    public void unregister(Host host) {
	}

    @POST
    @Consumes({"application/xml","application/json"})
    public void publish(Message message) {
	}

}
