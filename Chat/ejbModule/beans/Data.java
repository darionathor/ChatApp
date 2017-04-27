package beans;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Session Bean implementation class Data
 */
@Singleton
@LocalBean
@Startup
public class Data {
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
    public Data() {
        // TODO Auto-generated constructor stub
    	hostovi=new ArrayList<Host>();
    	main=true;
    }

}
