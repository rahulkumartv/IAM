package fr.rktv.iamcore.services.dao;

import java.util.List;

import fr.rktv.iamcore.datamodel.Identity;



/**
 * DAO responsible for handling  Operation such as Identity 
 * Creation, Deletion,Updation and Search
 * @author Rahul Thai Valappil
 * @version 1.0
 *
 */
public interface IdentityDAOInterface {

	
	/**
	 * Read all the identity details created in the Identity system 
	 * @return list of identity
	 */
	List<Identity> readAll();

	/**
	 * search particular identity with email adress
	 * @param identity -Identity object
	 * @return list of identities
	 */
	List<Identity> search(Identity identity);
	
	/**
	 * create an identity to the Identity system
	 * @param identity - Identity object
	 */
	void write(Identity identity);
	
	/**
	 * Update existing identity details
	 * @param identity -Identity object
	 */
	void update(Identity identity);
	
	/**
	 *Delete an existing identity from the system
	 * @param identity -Identity object
	 */
	void delete(Identity identity);

}
