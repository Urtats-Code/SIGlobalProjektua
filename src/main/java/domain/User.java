package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso ({Bazkide.class, Arduraduna.class})
@Entity 
public abstract class User implements Serializable{
	
	@XmlID
	@Id
	String EMAIL; 
	String USERNAME;
	String PASSWORD;
	
	public User(String user, String email, String password) {
		this.EMAIL = email; 
		this.USERNAME = user;
		this.PASSWORD = password;
	}
	
	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public User(){}

	public String getPassword() {
		return this.PASSWORD;
	}
	
	public String getEmail() {
		return this.EMAIL; 
	}
	
	public String getUserName() {
		return this.USERNAME;
	}
	
	
}
