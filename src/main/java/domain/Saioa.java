package domain;


import java.util.Queue;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Saioa implements Serializable{
	
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	Integer id;
	
	Date hasierakoData; 
	Date amaierakoData;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@XmlIDREF
	Queue<Erreserba> itxaroteLista;
	
	Jarduera SaiokoJarduera;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@XmlIDREF
	Vector<Erreserba> erreserbak; 
	
	Areto aretoa; 
	
	public Saioa(Date hdata, Date adata, Jarduera jarduera, Areto aretoa) {
		this.hasierakoData = hdata;
		this.amaierakoData = adata;
		this.erreserbak = new Vector<Erreserba>(); 
		this.itxaroteLista = new LinkedList<Erreserba>(); 
		this.SaiokoJarduera = jarduera; 
		this.aretoa = aretoa;
	}
	
	public Saioa() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getHasierakoData() {
		return hasierakoData;
	}

	public void setHasierakoData(Date hasierakoData) {
		this.hasierakoData = hasierakoData;
	}

	public Date getAmaierakoData() {
		return amaierakoData;
	}

	public void setAmaierakoData(Date amaierakoData) {
		this.amaierakoData = amaierakoData;
	}

	public Queue<Erreserba> getItxaroteLista() {
		return itxaroteLista;
	}

	public void setItxaroteLista(Queue<Erreserba> itxaroteLista) {
		this.itxaroteLista = itxaroteLista;
	}

	public Jarduera getSaiokoJarduera() {
		return SaiokoJarduera;
	}

	public void setSaiokoJarduera(Jarduera saiokoJarduera) {
		SaiokoJarduera = saiokoJarduera;
	}

	public Vector<Erreserba> getErreserbak() {
		return erreserbak;
	}

	public void setErreserbak(Vector<Erreserba> erreserbak) {
		this.erreserbak = erreserbak;
	}

	public Areto getAretoa() {
		return aretoa;
	}

	public void setAretoa(Areto aretoa) {
		this.aretoa = aretoa;
	}

	public void addErreserbaListara(Erreserba e) {
		this.erreserbak.add(e);
	}
	
	public void removeErreserba(Erreserba e ) {
		this.erreserbak.remove(e);
	}
	
	@Override
	public String toString() {
		return "Start: " + hasierakoData.getHours() + " End: " + amaierakoData.getHours() + " Activity: " + this.SaiokoJarduera.toString();
	}
}
