package beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.Message;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import exceptions.AliasExistsException;

/**
 * Session Bean implementation class RestManager
 */
@Path("/")
@Singleton
public class RestManager {
	@Inject ServerManager data;
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
    @Consumes({"application/xml","application/json"})
    public List<Host> register(@PathParam("param1") String address,@PathParam("param2") String alias) throws AliasExistsException {
		System.out.println(address);
		System.out.println(alias);
		ArrayList<Host> lista=data.getHostovi();
		Host e=new Host();
		e.setAddress(address);
		e.setAlias(alias);
		lista.add(e);
    	return lista;
	}

    @POST
    @Path("/unregister")
    @Consumes({"application/xml","application/json"})
    public void unregister(Host host) {
    	System.out.println("unregister");
    	for(Host h:data.getHostovi()){
    		if(h.getAddress().equals(host.getAddress())&&h.getAlias().equals(host.getAlias())){
    			data.getHostovi().remove(h);
    			break;
    		}
    	}
    	if(data.main){
    		
    	}
	}

    @POST
    @Path("/publish")
    @Consumes({"application/xml","application/json"})
    public void publish(Message message) {
	}

}
