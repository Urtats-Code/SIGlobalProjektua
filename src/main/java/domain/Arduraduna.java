package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Arduraduna extends User{

	public Arduraduna(String username, String email, String password) {
		super(username, email, password);
	}
	
	public Arduraduna() {
		super();
	}
	
}
