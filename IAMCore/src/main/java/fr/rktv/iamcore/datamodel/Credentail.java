package fr.rktv.iamcore.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The class responsible for storing User informations like
 * username and encrypted password 
 * user should have a valid license to register
 * @author Rahul Thai Valappil
 * @version 1.0
 */
@Entity
@Table(name="CREDENTAILS")
public class Credentail {
	
	/**
	 * unique id of a User
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;

	/**
	 * user name of a User
	 */
	@Column(name="USERNAME")
	private String username;
	
	/**
	 * encrypted password of a User
	 */
	@Column(name="PASSWORD")
	private String password;
	
	/**
	 * valid license of a User
	 */
	@Column(name="License")
	private String license;

	/**
	 * default constructor
	 */
	public Credentail(){
		 // The explicit constructor is here, so that it is possible to provide Javadoc.
	}
	
	/**
	 * Constructor to initiate user object from username and password
	 * @param username - String
	 * @param password - String
	 */
	public Credentail(final String username, final String password)
	{
		this.username = username;
		this.password = password;
	}
	
	/**
	 * @return the configured license corresponds to a user 
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @param license -string
	 * Set valid license for a user
	 */
	public void setLicense(final String license) {
		this.license = license;
	}

	/**
	 * @return unique id corresponds to a user
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param uid - int
	 * Set unique id to user
	 */
	public void setUserId(final int uid) {
		this.userId = uid;
	}

	/**
	 * @return user name of a user 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param userName - string
	 * set username to user
	 */
	public void setUsername(final String userName) {
		this.username = userName;
	}

	/**
	 * @return encrypted password from a user 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password- string
	 * set password to a user
	 */
	public void setPassword(final String password) {
		this.password = password;
	}
	
	/**
	 * converts user object to json format
	 */
	@Override
	public String toString() {
		return "{username:" + username + ", password:" + password +"}";
	}
}
