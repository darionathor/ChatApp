package main;

import java.util.List;

import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.DataBean;
import beans.MessageClient;
import beans.User;
import beans.UserApp;
import exceptions.InvalidCredentialsException;
import exceptions.UsernameExistsException;

@Path("/")
public class Rest {
	@EJB DataBean data;
	@EJB UserApp ua;
	
	@GET
    @Path("/test")
    public String test() {
		return "stuff";
		
	}
	@GET
    @Path("/register/{param1}/{param2}")
    @Produces(MediaType.APPLICATION_JSON)
    public User register(@PathParam("param1")String username,@PathParam("param2") String password) throws UsernameExistsException {
    	System.out.println("bloody register");
    	
    	return ua.register(username, password);
	}

    @GET
    @Path("/login/{param1}/{param2}")
    @Produces(MediaType.TEXT_PLAIN)
    public Boolean login(@PathParam("param1") String username,@PathParam("param2")  String password,@javax.ws.rs.core.Context HttpServletRequest request) throws InvalidCredentialsException {
    	System.out.println("bloody login");
    	
    	return ua.login(username, password);
	}

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean logout(User logout) {
    	return ua.logout(logout);
	}

    @GET
    @Path("/registeredUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
		return data.registeredUsers;
	}
}
