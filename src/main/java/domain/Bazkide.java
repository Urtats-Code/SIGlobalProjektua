package domain;



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessorType;

import java.io.Serializable;
import java.util.Vector;import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Bazkide extends User{
	int ErreserbaKop; 
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@XmlIDREF
	Vector<Erreserba> ErreserbaLista; 
	
	public Bazkide(String username, String email, String password) {
		super(username, email, password);
		this.ErreserbaKop = 0; 
		ErreserbaLista = new Vector<Erreserba>();
	} 
	
	public Bazkide() { super(); }
	
	
	public void incrementErreserbaKop() {
		this.ErreserbaKop += 1;
	} 
	
	public void decrementErreserbaKop() {
		if(this.ErreserbaKop > 0) {			
			this.ErreserbaKop -= 1;
		}
	}
	
	public void addErreserba(Erreserba erreserba) {
		this.ErreserbaLista.add(erreserba);
	}
	
	public void deleteErreserba(Erreserba erreserba) {
		this.ErreserbaLista.remove(erreserba);
	}
	
	public void checkForErreserba(Erreserba erreserba) {
		this.ErreserbaLista.contains(erreserba);
	}


	public int getErreserbaKop() {
		return ErreserbaKop;
	}


	public void setErreserbaKop(int erreserbaKop) {
		ErreserbaKop = erreserbaKop;
	}


	public Vector<Erreserba> getErreserbaLista() {
		return ErreserbaLista;
	}

	public void removeErreserba(Erreserba e) {
		try {
		this.ErreserbaLista.remove(ErreserbaKop);
		}catch(Exception e1){
			System.out.println("Nothing happended");
		}
	}
	
	
	public void setErreserbaLista(Vector<Erreserba> erreserbaLista) {
		ErreserbaLista = erreserbaLista;
	}
	
}
