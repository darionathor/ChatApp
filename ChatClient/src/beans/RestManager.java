package beans;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jms.Message;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.AliasExistsException;

/**
 * Session Bean implementation class RestManager
 */
@Path("/")
public class RestManager {
	@Inject ServerManager data;
	@Inject SocketManager sm;
    /**
     * Default constructor. 
     */
    @GET
    @Path("/test")
    public String test() {
		return "stuff";
		
	}
    @GET
    @Path("/register/{param1}/{param2}")
    @Produces({"application/xml","application/json"})
    public List<Host> register(@PathParam("param1") String address,@PathParam("param2") String alias) throws AliasExistsException {
		return data.register(address,alias);
	}

    @POST
    @Path("/unregister")
    @Consumes({"application/xml","application/json"})
    public void unregister(Host host) {
    	data.unregister(host);
	}

    @POST
    @Path("/publish")
    @Consumes({"application/xml","application/json"})
    public void publish(Message message) {
	}
    @POST
    @Path("/login")
    @Consumes({"application/xml","application/json"})
    public void login(User user) {
    	System.out.println("login attempted on sec server");
    	sm.addUser(user);
    	
	} @Path("/logout")
    @Consumes({"application/xml","application/json"})
    public void logout(User user) {
    	sm.removeUser(user);
    	
	}
}
