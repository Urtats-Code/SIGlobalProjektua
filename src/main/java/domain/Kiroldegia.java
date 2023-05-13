package domain;

import java.io.Serializable;
import java.util.Vector;
import javax.persistence.*;
import java.util.Vector;import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Kiroldegia implements Serializable{
	
	@XmlID
	@Id
	String name;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	Vector<Areto> aretoak;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	Vector<Jarduera> jarduerak;
	
	public Kiroldegia(Vector<Areto> aretoak, Vector<Jarduera> jarduerak ) {
		this.name="EHU Kiroldegia";
		this.aretoak = aretoak;
		this.jarduerak=jarduerak;
	}
	
	public Kiroldegia() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Areto> getAretoak() {
		return aretoak;
	}

	public void setAretoak(Vector<Areto> aretoak) {
		this.aretoak = aretoak;
	}

	public Vector<Jarduera> getJarduerak() {
		return jarduerak;
	}

	public void setJarduerak(Vector<Jarduera> jarduerak) {
		this.jarduerak = jarduerak;
	}
	
	
	
}
