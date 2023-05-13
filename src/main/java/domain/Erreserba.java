package domain;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.*;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Erreserba implements Serializable{


	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	Integer ID; 
	Date date;
	boolean mode;
	
	
	Saioa saioa; 
	
	
	public void setID(Integer iD) {
		ID = iD;
	}

	public void setSaioa(Saioa saioa) {
		this.saioa = saioa;
	}

	
	
	public Erreserba(Date data, boolean mode, Saioa s) {
		this.mode = mode; 
		this.date = data;
		this.saioa = s;
	}
	
	public Erreserba() {}

	public Saioa getSaioa() {
		return this.saioa;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean getMode() {
		return mode;
	}

	public void setMode(boolean mode) {
		this.mode = mode;
	}

	public Integer getID() {
		return ID;
	}

	@Override
	public String toString() {
		return "Erreserba " + this.saioa.SaiokoJarduera.toString() + " at " + this.saioa.getHasierakoData().getHours() + ":" + this.saioa.getHasierakoData().getMinutes();
	}
	
	
}
