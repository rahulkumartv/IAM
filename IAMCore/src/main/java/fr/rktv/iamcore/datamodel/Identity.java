/**
 * 
 */
package fr.rktv.iamcore.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents the data model of Identity information 
 * @author Rahul Thai Valappil
 * @version 1.0
 */
@Entity
@Table(name="IDENTITIES")
public class Identity {
	
	/**
	 * unique id for an identity
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int identId;
	
	/**
	 * first name of an  identity
	 */
	@Column(name="IDENTITY_FIRSTNAME")
	private String firstName;
	
	/**
	 * last name of an identity
	 */
	@Column(name="IDENTITY_LASTNAME")
	private String lastName;
	
	/**
	 * email of an identity
	 */
	@Column(name="IDENTITY_EMAIL")
	private String email;
	
	/**
	 * birth date of an identity
	 */
	@Column(name="IDENTITY_BIRTHDATE")
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	
	/**
	 * default constructor
	 */
	public Identity(){
		 // The explicit constructor is here, so that it is possible to provide Javadoc. 
	}
	
	/**
	 * constructor to create identity object from its first name, last name and email
	 * @param firstName - String
	 * @param lastName  - String
	 * @param email  - String
	 */
	public Identity(final String firstName, final String lastName, final String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	/**
	 * converts Identity object to JSON string format
	 */
	@Override
	public String toString() {
		return "Identity [firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", birthDate=" + birthDate + "]\n";
	}
	
	/**
	 * @return the firstName of an Identity
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param firstName - string
	 * set first name to an identity
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return the lastName from Identity
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @param lastName - string
	 * set lastName to identity
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the email from the identity
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email - string
	 * set email address to identity
	 */
	public void setEmail(final String email) {
		this.email = email;
	}
	
	/**
	 * @return the birthDate from identity object
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	
	/**
	 * @param birthDate -Date 
	 * Set the birth dare of an identity
	 */
	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}
	
	/**
	 * @return unique id of an Identity created automatically
	 */
	public int getIdentId() {
		return identId;
	}
	
	/**
	 * @param uid - int
	 * Set unique id to identity object 
	 */
	public void setIdentId(final int uid) {
		this.identId = uid;
	}
}
