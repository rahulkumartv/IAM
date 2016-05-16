package fr.rktv.iamcore.test.hibernate;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import fr.rktv.iamcore.datamodel.Credentail;
import fr.rktv.iamcore.datamodel.Credentail;
import fr.rktv.iamcore.services.dao.AuthDAOInterface;


/**
 * Class to test the functionalities of Authenticate DAO  
 * @author Rahul Thai Valappil
 * @version 1.0
 */
//This is to tell Junit to run with spring
@RunWith(SpringJUnit4ClassRunner.class) 
//to tell spring to load the required context
@ContextConfiguration(locations={"file:src/test/resources/application-context.xml"}) 
public class AuthHibernateDAOTest {

	@Autowired
	@Qualifier("Lincese_Key")
	String license;
	@Autowired
	@Qualifier("AuthenticationDAO")
	private AuthDAOInterface authenticationDAO;
	
	
	/**
	 * Test case for AddUser Method of Authenticate DAO
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void addUserTest() throws IllegalArgumentException, IllegalAccessException{
		
		final Credentail user = new Credentail("testuser","test");
		user.setLicense(license);
		final Credentail newUser = new Credentail("testuser","test");
		authenticationDAO.addUser(user);
		Assert.isTrue(authenticationDAO.checkUserAuthentication(newUser));
	}
	
	/**
	 * Test case for checkUserAuthentication method of Authenticate DAO
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void checkUserAuthTest() throws IllegalArgumentException, IllegalAccessException{
		final Credentail newUser = new Credentail("testuser11","test12");
		Assert.isTrue(!authenticationDAO.checkUserAuthentication(newUser));
	}
	
	/**
	 * Test case checking success case of licensedUserAlreadyExist method of Authenticate DAO
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void licesUsrAlreadyExistTestSucc() throws IllegalArgumentException, IllegalAccessException{
		final Credentail user = new Credentail("testuser","test");
		user.setLicense(license);
		authenticationDAO.addUser(user);
		Assert.isTrue(authenticationDAO.licensedUserAlreadyExist(license));
	}
	
	/**
	 * Test case checking failure case of licensedUserAlreadyExist method of Authenticate DAO
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void licesUsrAlreadyExistTestFail() throws IllegalArgumentException, IllegalAccessException{
		final Credentail user = new Credentail("testuser","test");
		user.setLicense(license);
		authenticationDAO.addUser(user);
		Assert.isTrue(!authenticationDAO.licensedUserAlreadyExist("23a4a4bd-83a9-4b95-be9a-638bf5fd3534"));
	}
	
	/**
	 * Test case checking success case of checkValidLicense method of Authenticate DAO
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void checkValidLicenseTestSucc() throws IllegalArgumentException, IllegalAccessException{
		
		Assert.isTrue(authenticationDAO.checkValidLicense(license));
	}
	

	/**
	 * Test case checking failure case of checkValidLicense method of Authenticate DAO
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void checkValidLicenseTestFail() throws IllegalArgumentException, IllegalAccessException{
		
		Assert.isTrue(!authenticationDAO.checkValidLicense("ABCDERGDFDJHDJJDD"));
	}

}
