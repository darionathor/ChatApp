package beans;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Session Bean implementation class ServerManager
 */
@Startup
@Singleton
public class ServerManager {
	ArrayList<Host> hostovi;
	boolean main;

    public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public ArrayList<Host> getHostovi() {
		return hostovi;
	}

	public void setHostovi(ArrayList<Host> hostovi) {
		this.hostovi = hostovi;
	}

	/**
     * Default constructor. 
     */
	String  host;
	Integer port;
	boolean mainServer;
    /**
     * Default constructor. 
     */
	@PostConstruct
    public void initialize() {
    }
    @PreDestroy
    public void terminate() {
    	System.out.println("terminate");
    	if(!mainServer){
 			String uri =
 				    "http://localhost:8080/ChatClient/rest/unregister/";
 				URL url;
				try {
					url = new URL(uri);
				
 				HttpURLConnection connection =
 				    (HttpURLConnection) url.openConnection();
 				connection.setRequestMethod("POST");
 				connection.setDoOutput(true);
 				connection.setRequestMethod("POST");
 				connection.setRequestProperty("Content-Type", "application/json");
 				ObjectMapper om= new ObjectMapper();

 				OutputStream xml = connection.getOutputStream();
 				Host hst= new Host();
 				hst.setAddress(host);
 				hst.setAlias(port.toString());
 				String out= om.writeValueAsString(hst);
 				PrintWriter pw=new PrintWriter(xml);
 				pw.write(out);
 				connection.disconnect();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 		}
    }
    public ServerManager() {
        // TODO Auto-generated constructor stub
    	hostovi=new ArrayList<Host>();
    	main=true;
    	MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
  		ObjectName socketBindingMBean;
 			try {
 				socketBindingMBean = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");

 	    		host = (String)  mBeanServer.getAttribute(socketBindingMBean, "boundAddress");
 	    		port = (Integer) mBeanServer.getAttribute(socketBindingMBean, "boundPort");
 	    		System.out.println(host);
 	    		System.out.println(port);
 	    		if(!port.equals(new Integer(8080))){
 	    			mainServer=false;
 	    			main=false;
 	    			System.out.println("secondary server");
 	    			String uri =
 	    				    "http://localhost:8080/ChatClient/rest/register/"+host+"/"+port;
 	    				URL url = new URL(uri);
 	    				HttpURLConnection connection =
 	    				    (HttpURLConnection) url.openConnection();
 	    				connection.setRequestMethod("GET");
 	    				connection.setRequestProperty("Accept", "application/json");
 	    				ObjectMapper om= new ObjectMapper();

 	    				InputStream xml = connection.getInputStream();
 	    				ArrayList<Host> hostovi=om.readValue(xml, new TypeReference<ArrayList<Host>>(){});
 	    				System.out.println(hostovi.get(0).getAddress());
 	    				setHostovi(hostovi);
 	    				connection.disconnect();
 	    		}
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 
    }

}
