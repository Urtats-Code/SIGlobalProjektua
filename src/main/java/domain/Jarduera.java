package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.*;
import java.util.Vector;import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Jarduera implements Serializable{
	
	@XmlID
	@Id
	String Izena;
	Integer EskakizunMaila;

	public Jarduera(String name, Integer sportyLevel) {
		Izena = name;
		EskakizunMaila = sportyLevel;
	}
	
	public Jarduera() {}

	public String getIzena() {
		return Izena;
	}

	public void setIzena(String izena) {
		Izena = izena;
	}

	public Integer getEskakizunMaila() {
		return EskakizunMaila;
	}

	public void setEskakizunMaila(Integer eskakizunMaila) {
		EskakizunMaila = eskakizunMaila;
	}

	
	public String toString() 
	{
		return this.Izena ; 
	
	}
}
