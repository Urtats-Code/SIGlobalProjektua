package domain;



import java.io.Serializable;
import java.util.Vector;
import domain.Saioa;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Areto implements Serializable{
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	Integer id;
	int AFOROMAXIMO; 
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@XmlIDREF
	Vector<domain.Saioa> saioak;
	
	public Areto(int AFOROA, Integer id) {
		this.id=id;
		this.AFOROMAXIMO = AFOROA;
		this.saioak = new Vector<domain.Saioa>();
	}

	public Areto() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getAFOROMAXIMO() {
		return AFOROMAXIMO;
	}

	public void setAFOROMAXIMO(int aFOROMAXIMO) {
		AFOROMAXIMO = aFOROMAXIMO;
	}

	public Vector<Saioa> getSaioak() {
		return saioak;
	}

	public void setSaioak(Vector<Saioa> saioak) {
		this.saioak = saioak;
	}

	public void addSaioa(Saioa s) {
		this.saioak.add(s);
	}
	
	public void removeSaioa(Saioa s) {
		this.saioak.remove(s);
	}
	
	@Override
	public String toString() {
		return "Areto " + id;
	}
	
}
