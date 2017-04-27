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
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.persistence.Entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Session Bean implementation class ServerManager
 */
@Startup
@Singleton
@Entity
public class ServerManager {
	ArrayList<Host> hostovi=new ArrayList<Host>();
	ArrayList<User> useri;
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
 	    				
 	    				uri =
 	 	    				    "http://localhost:8080/UserRest/rest/registeredUsers";
 	 	    				url = new URL(uri);
 	 	    				connection =
 	 	    				    (HttpURLConnection) url.openConnection();
 	 	    				connection.setRequestMethod("GET");
 	 	    				connection.setRequestProperty("Accept", "application/json");
 	 	    				

 	 	    				xml = connection.getInputStream();
 	 	    				useri=om.readValue(xml, new TypeReference<ArrayList<User>>(){});
 	 	    				
 	 	    				connection.disconnect();
 	    		}
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 
    }

	public void addUser(User user) {
		// TODO Auto-generated method stub
		System.out.println(hostovi.size());
		for(Host h:hostovi){
			String uri =
 				    "http://"+h.address+":"+h.alias+"/ChatClient/rest/login";
			System.out.println(uri);
			System.out.println(user.getUsername()+user.getPassword());
 				URL url;
				try {
					url = new URL(uri);
				
 				HttpURLConnection connection =
 				    (HttpURLConnection) url.openConnection();
 				connection.setDoOutput(true);
 				connection.setRequestMethod("POST");
 				connection.setRequestProperty("Content-Type", "application/json");
 				ObjectMapper om= new ObjectMapper();

 				OutputStream xml = connection.getOutputStream();
 				
 				String out= om.writeValueAsString(user);
 				PrintWriter pw=new PrintWriter(xml);
 				pw.write(out);
 				connection.disconnect();
				}catch(Exception e){
					e.printStackTrace();
				}
		};
	}

	public void removeUser(User user) {
		// TODO Auto-generated method stub
		for(Host h:hostovi){
			String uri =
 				    "http://"+h.address+":"+h.alias+"/ChatClient/rest/logout/";
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
 				
 				String out= om.writeValueAsString(user);
 				PrintWriter pw=new PrintWriter(xml);
 				pw.write(out);
 				connection.disconnect();
				}catch(Exception e){
					e.printStackTrace();
				}
		};
	}

	public ArrayList<Host> register(String address, String alias) {
		// TODO Auto-generated method stub
		System.out.println(address);
		System.out.println(alias);
		ArrayList<Host> lista=getHostovi();//data.getHostovi();
		Host e=new Host();
		e.setAddress(address);
		e.setAlias(alias);
		for(Host h: lista){
			if(h.getAddress().equals(address)&& h.getAlias().equals(alias)){
				return lista;
			}
		}
		lista.add(e);
    	return lista;
	}

	public void unregister(Host host2) {
		// TODO Auto-generated method stub
		System.out.println("unregister");
    	for(Host h:getHostovi()){
    		if(h.getAddress().equals(host2.getAddress())&&h.getAlias().equals(host2.getAlias())){
    			getHostovi().remove(h);
    			break;
    		}
    	}
    	if(main){
     			String uri =
     				    "http://localhost:8080/ChatClient/rest/unregister/";
     				URL url;
    				try {
    					url = new URL(uri);
    				
     				HttpURLConnection connection =
     				    (HttpURLConnection) url.openConnection();
     				connection.setRequestMethod("POST");
     				connection.setDoOutput(true);
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

}
