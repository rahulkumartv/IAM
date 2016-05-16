package fr.rktv.iamcore.services.dao;

import fr.rktv.iamcore.datamodel.Credentail;


/**
 * DAO interface responsible for user authentication and registration
 * @author Rahul Thai Valappil
 * @version 1.0
 * 
 */
public interface AuthDAOInterface {

	/**
	 * Add user to the Authentication system with valid license.
	 * So the user will have permission make changes to the Identity system
	 * @param user - Credentail details
	 */
	void addUser( Credentail user);
	/**
	 * check whether a user exist for a license
	 * @param license 0 valid license
	 * @return true if same licensed user already exists other wise false
	 */
	boolean licensedUserAlreadyExist( String license);
	/**
	 * check the validity of  the user credential
	 * @param user - Credentail details
	 * @return true if Authentication otherwise false
	 */
	boolean checkUserAuthentication( Credentail user);
	/**
	 * Authenticate license
	 * @param license - valid license
	 * @return true if license is valid otherwise false
	 */
	boolean checkValidLicense( String license);
}
