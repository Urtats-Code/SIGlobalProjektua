package businessLogic;

import java.util.Vector;

import java.util.Date;





import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.Areto;
import domain.Jarduera;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	/**
	 * This method registers a user 
	 */	
	@WebMethod public boolean register(String username, String email, String password);
	 
	/**
	 * This method says if the user is in the database 
	 * @return -1 if does not exists
	 * @return 0 if it is a client
	 * @return 1 if it is an admin
	 */	
	 @WebMethod 
	 public Integer login(String email, String password);
	 
	 /**
	  * This method saves a new activity in the database 
	  */	
	 @WebMethod 
	 public boolean saveActivity(String name, Integer sportyLevel);
	 
	 @WebMethod
	 public Vector<Jarduera> logActivities();
	 
	 @WebMethod 
	 public Vector<Areto> logAretoak();
	 
	 @WebMethod
	 public Vector<domain.Saioa> logSessions();
	 
	 @WebMethod
	 public boolean addSaioa(domain.Saioa s);
	 
	 @WebMethod
	 public Vector<domain.Saioa> logActivitiesForThisDay(Date data);

	 @WebMethod
	 public boolean addReservation(domain.Erreserba e , String email);
	 
	 @WebMethod
	 public Vector<domain.Saioa> logActivitiesByDifficultyLvl(int difficultyLevel);
	 
	 @WebMethod
	 public Vector<domain.Erreserba> logReservationsByDate(Date data);
	 
	 @WebMethod
	 public boolean removeReservation(domain.Erreserba e, String email);
}

