package businessLogic;
import java.util.Date;


import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Areto;
import domain.Jarduera;
import domain.Saioa;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
		    dbManager.initializeDB();
		    } else
		     dbManager=new DataAccess();
		dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
    
    @WebMethod
    public boolean register(String username, String email, String password) {
    	dbManager.open(false);
    	boolean register = dbManager.register(username, email, password);
    	dbManager.close(); 
    	return register;
    }
    
	@Override
	public Integer login(String email, String password) {
		dbManager.open(false);
    	Integer num = dbManager.login(email, password);
    	dbManager.close();
    	return num;
	}

	@Override
	public boolean saveActivity(String name, Integer sportyLevel) {
		dbManager.open(false);
    	boolean exists = dbManager.saveActivity(name, sportyLevel);
    	dbManager.close();
    	return exists;
	}
	
	@Override
	public Vector<Jarduera> logActivities(){
		dbManager.open(false);
		Vector<Jarduera> activitiesList = dbManager.logActivities();
		dbManager.close();
		if(!activitiesList.equals(null)) return activitiesList;
		else return new Vector<Jarduera>();
	}
	
	@Override
	public Vector<Areto> logAretoak() {
		dbManager.open(false);
		Vector<Areto> aretoak = dbManager.logAretoak();
		dbManager.close();
		if(!aretoak.equals(null)) return aretoak;
		else return new Vector<Areto>(); 
	}

	@Override
	public Vector<domain.Saioa> logSessions(){
		dbManager.open(false);
		Vector<domain.Saioa> saioak = dbManager.logSessions();
		dbManager.close();
		if(!saioak.equals(null)) return saioak;
		else return new Vector<domain.Saioa>(); 
	}
	
	@Override
	public boolean addSaioa(Saioa s) {
		dbManager.open(false);
		boolean response = dbManager.addSaioa(s);
		dbManager.close();
		return response;
	}
	
	
	@Override
	public Vector<domain.Saioa> logActivitiesForThisDay(Date data){
		dbManager.open(false);
		Vector<domain.Saioa> sessions = dbManager.logActivitiesForThisDay(data);
		dbManager.close();
		return sessions;
	}
	
	@Override
	public boolean addReservation(domain.Erreserba reservation , String email) {
		dbManager.open(false);
		boolean response = dbManager.addReservation(reservation, email);
		dbManager.close();
		return response;
	}
	
	
	@Override
	public Vector<domain.Saioa> logActivitiesByDifficultyLvl(int difficultyLevel){
		dbManager.open(false);
		Vector<domain.Saioa> response = dbManager.logActivitiesByDifficultyLvl(difficultyLevel);
		dbManager.close(); 
		return response;
	}
	
	@Override 
	public Vector<domain.Erreserba> logReservationsByDate(Date date){
		dbManager.open(false);
		Vector<domain.Erreserba> erreserba = dbManager.logReservationsByDate(date);
		dbManager.close(); 
		return erreserba;
	}
	
	@Override 
	public boolean removeReservation(domain.Erreserba e, String email) {
		dbManager.open(false);
		boolean response = dbManager.removeReservation(e, email);
		dbManager.close();
		return response;
	}
}

