package dataAccess;

import java.util.Calendar;
import domain.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.objectdb.o.CLN.s;

import configuration.ConfigXML;
import configuration.UtilDate;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess(boolean initializeMode)  {
		
		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);
		
	}

	public DataAccess()  {	
		 this(false);
	}
	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();
		try {
		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   month+=1;
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=0; year+=1;} 
		   	
		   Arduraduna admin1 = new Arduraduna("admin", "admin@admin.com", "admin");
		   
		   Bazkide user1 = new Bazkide("user1", "user1@gmail.com", "user1"); 
		   Bazkide user2 = new Bazkide("user2", "user2@gmail.com", "user2"); 
		   
		   Jarduera j1 = new Jarduera("Pilates", 2);
		   Jarduera j2 = new Jarduera("Escalada", 4);
		   Jarduera j3 = new Jarduera("Natacion", 3);
		  
		   Areto a1=new Areto(1, 1);
		   Areto a2=new Areto(190, 2);
		   Areto a3=new Areto(180, 3);
		   Areto a4=new Areto(170, 4);
		   Areto a5=new Areto(160, 5);
		   
		   Date data1 = new Date();
		   Date dataEnd1 = new Date();
		   dataEnd1.setHours(data1.getHours() + 1);
		   data1.setSeconds(0);
		   dataEnd1.setSeconds(0);
		   
		   Date data2 = new Date();
		   Date dataEnd2 = new Date();
		   dataEnd2.setHours(data2.getHours() + 1);
		   data2.setSeconds(0);
		   dataEnd2.setSeconds(0);
		   
		   Saioa s1 = new Saioa(data1, dataEnd1, j1, a1);
           a1.addSaioa(s1);

           Saioa s2 = new Saioa(data2, dataEnd2, j2, a2); 
           a1.addSaioa(s1);
		  
           db.persist(admin1);
		   db.persist(user1);
		   db.persist(user2);
		   
		   db.persist(j1);
		   db.persist(j2);
		   db.persist(j3);
		   
		   db.persist(a1);
		   db.persist(a2);
		   db.persist(a3);
		   db.persist(a4);
		   db.persist(a5);
		   
		   db.persist(s1);
		   db.persist(s2);
		   
		   
		   
		   //Error al confirmar la transacción: 
		   //intento de conservar una referencia a un dominio no administrado. 
		   //Instancia de Saioa: campo dominio. Areto.saioak (error 613)
		   db.getTransaction().commit();
		   System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	

public void open(boolean initializeMode){
		
		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Deleting the DataBase");
		}
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}
	
	public boolean register(String username, String email, String password) {
		Bazkide newUser = new Bazkide(username, email, password);
		// check if the user exists in the data base 
		if(db.find(domain.User.class, email) != null) return false;
		
		// add the user
		db.getTransaction().begin();
		db.persist(newUser);
		db.getTransaction().commit();
		
		return true;
	}
	
	
	public Integer login(String email, String password) {
		User dataBaseUser = db.find(User.class, email);
		// check if user exists
		if(dataBaseUser == null) return -1;
		// check it the from the data base is 
		if(dataBaseUser.getEmail().equals(email) && dataBaseUser.getPassword().equals(password)) {
			//If it is a client
			if(dataBaseUser.getClass().equals(domain.Bazkide.class)) {
				return 0;
			}
			//If it is an admin
			else
				return 1;
		}
		// the user does not exists
		return -1;
		
		
	}

	public boolean saveActivity(String name, Integer sportyLevel) {
		Jarduera j=new Jarduera(name, sportyLevel);
		// check if the activity exists in the data base 
		if(db.find(Jarduera.class, j) != null) {
			return false;
		}
		
		// add the activity
		db.getTransaction().begin();
		db.persist(j);
		db.getTransaction().commit();
		return true;
	}
	
	
	public Vector<Jarduera> logActivities(){
		Vector<Jarduera> activities = new Vector<Jarduera>();
		TypedQuery<Jarduera> query = db.createQuery("SELECT j FROM Jarduera j", Jarduera.class);
		List<Jarduera> list = query.getResultList();
		for (Jarduera jarduera : list) {
			activities.add(jarduera);
		}
		
		return activities;
	}
	
	public Vector<Areto> logAretoak(){
		Vector<Areto> aretoak = new Vector<Areto>(); 
		TypedQuery<Areto> query = db.createQuery("SELECT a FROM Areto a", Areto.class);
		List<Areto> list = query.getResultList(); 
		for(Areto areto : list) {
			aretoak.add(areto);
		}
		
		return aretoak;
	}
	
	public Vector<Saioa> logSessions(){
		Vector<Saioa> saioak = new Vector<Saioa>();
		TypedQuery<Saioa> query = db.createQuery("SELECT a FROM Saioa a ", Saioa.class);
		List<Saioa> list = query.getResultList();
		for(Saioa saioa : list) {
			saioak.add(saioa);
		}
		
		return saioak;
	}
	
	public boolean addSaioa(domain.Saioa s) {
		
		System.out.println(s);
		
		// check if the session exists
		if(db.find(domain.Saioa.class, s) != null) return false;
		
		domain.Areto aretoBerria = db.find(domain.Areto.class, s.getAretoa().getId());
		
		// check if a session is already running at those hours 
		
		TypedQuery<Saioa> checkIfExistsSession = db.createQuery("SELECT a FROM Saioa a WHERE (a.hasierakoData BETWEEN ?1 AND ?2) OR (a.amaierakoData BETWEEN ?1 AND ?2) AND a.aretoa = ?3", domain.Saioa.class);
		Date start = s.getHasierakoData();
		Date end = s.getAmaierakoData();
		checkIfExistsSession.setParameter(1, start);
		checkIfExistsSession.setParameter(2, end);
		checkIfExistsSession.setParameter(3, aretoBerria);
		List<Saioa> list = checkIfExistsSession.getResultList();
		System.out.println(list.size());
		if(list.size() != 0) return false;
		
		
		// add the new session to the hall
	
		aretoBerria.addSaioa(s);

		// update hall
		db.getTransaction().begin();
		db.persist(aretoBerria);
		db.getTransaction().commit(); 

		// add session to the data base 
		db.getTransaction().begin();
		db.persist(s);
		db.getTransaction().commit();

		// passed all the test
		return true;
	}
	
	
	public boolean addReservation(domain.Erreserba e, String email) {
		
		// the hall is full 
		
		if( e.getSaioa().getErreserbak() != null && e.getSaioa().getAretoa().getAFOROMAXIMO() == e.getSaioa().getErreserbak().size()) {
			e.getSaioa().getItxaroteLista().add(e);
			return false; 
		}
		
		// the reservationa already exists
		
		if(db.find(domain.Erreserba.class , e) != null) return false;
		
		
		// the user has 15 reservations
		
		domain.Bazkide user = db.find(domain.Bazkide.class, email);
		if(user.getErreserbaKop() >= 15) return false;
		
		// user.incrementErreserbaKop();
		// user.addErreserba(e);
		
		// e.getSaioa().addErreserbaListara(e);
		
		// add the reservation to the list
		
		db.getTransaction().begin();
		domain.Saioa s = db.find(domain.Saioa.class, e.getSaioa());
		s.addErreserbaListara(e);
		db.persist(s);
		db.persist(e);
		db.getTransaction().commit();
		
		return true;
	}
	
	public Vector<domain.Saioa> logActivitiesForThisDay(Date date){
		Vector<domain.Saioa> sessions = new Vector<domain.Saioa>(); 
		Date start = date; 
		start.setHours(0);
		start.setMinutes(0);
		start.setSeconds(0);
		
		Date end = (Date) date.clone();
		end.setHours(23);
		end.setMinutes(59);
		end.setSeconds(59);
		
		TypedQuery<Saioa> query = db.createQuery("SELECT a FROM Saioa a WHERE a.hasierakoData BETWEEN ?1 AND ?2", Saioa.class);
		query.setParameter(1, start);
		query.setParameter(2, end);
		List<Saioa> list = query.getResultList();
		for(Saioa saioa : list) {
			sessions.add(saioa);
		}
		
		return sessions;
	}
	
	public Vector<domain.Saioa> logActivitiesByDifficultyLvl(int difficultyLevel){
		
		Vector<domain.Saioa> sessions = new Vector<domain.Saioa>();
		TypedQuery<domain.Saioa> query = db.createQuery("SELECT a FROM Saioa a WHERE a.SaiokoJarduera.EskakizunMaila = ?1" , domain.Saioa.class);
		query.setParameter(1, difficultyLevel);
		List<Saioa> list = query.getResultList();
		
		for(Saioa saioa : list) {
			sessions.add(saioa);
		}
		
		return sessions;
	}
	
	public Vector<domain.Erreserba> logReservationsByDate(Date date){
		Vector<domain.Erreserba> reservations = new Vector<domain.Erreserba>(); 
		Date start = date; 
		start.setHours(0);
		start.setMinutes(0);
		start.setSeconds(0);
		
		Date end = (Date) date.clone();
		end.setHours(23);
		end.setMinutes(59);
		end.setSeconds(59);
		
		TypedQuery<Erreserba> query = db.createQuery("SELECT a FROM Erreserba a WHERE a.saioa.hasierakoData BETWEEN ?1 AND ?2", domain.Erreserba.class);
		query.setParameter(1, start);
		query.setParameter(2, end);
		List<domain.Erreserba> list = query.getResultList();
		for(domain.Erreserba erreserba : list) {
			reservations.add(erreserba);
		}
		
		return reservations;
	}
	
	
	public boolean removeReservation(domain.Erreserba e, String email) {
		
		domain.Erreserba  removing = db.find(domain.Erreserba.class, e);
		Bazkide user = db.find(domain.Bazkide.class, email);
		
		if(user == null) return false;
		
		
		db.getTransaction().begin();
		user.decrementErreserbaKop();
		user.removeErreserba(removing);
		db.persist(user);
		db.getTransaction().commit();
		
		
		db.getTransaction().begin();
		e.getSaioa().removeErreserba(removing);
		if(e.getSaioa().getItxaroteLista().size() != 0) { 
			domain.Erreserba newReservation = removing.getSaioa().getItxaroteLista().poll();
			newReservation.setMode(true);
			addReservation(newReservation, email);
		}
		db.persist(removing.getSaioa());
		db.getTransaction().commit();

		
		db.getTransaction().begin();
		db.remove(removing); 
		db.getTransaction().commit();
		
		
		return true;
	}
}


