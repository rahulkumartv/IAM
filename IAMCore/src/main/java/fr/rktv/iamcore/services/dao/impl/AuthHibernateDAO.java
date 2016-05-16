package fr.rktv.iamcore.services.dao.impl;

import java.util.List;

import org.apache.log4j.Level;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jasypt.digest.PooledStringDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.rktv.iamcore.datamodel.Credentail;
import fr.rktv.iamcore.logger.LogManager;
import fr.rktv.iamcore.services.dao.AuthDAOInterface;

/**
 * DAO class implements user authentication using AuthDAOInterface 
 * With help of hibernate and Datasource storing user details to derby database 
 * @author Rahul Thai Valappil
 * @version 1.0 
 */
public class AuthHibernateDAO implements AuthDAOInterface{

	/**
	 * search user query
	 */
	@Autowired
	@Qualifier("searchUser")
	private String searchUser;
	
	/**
	 * hibernate session factory object corresponds to the User authentication
	 */
	@Autowired
	@Qualifier("beanBasedSessionFactory")
	private SessionFactory factory;
	
	/**
	 * digest for input password
	 */
	@Autowired
	@Qualifier("stringDigester")
	private PooledStringDigester stringDigester;
	
	/**
	 * license configured in the system
	 */
	@Autowired
	@Qualifier("Lincese_Key")
	private String defltLicense;
	
	/**
	 * default constructor
	 */
	public AuthHibernateDAO()
	{
		// no implementation for default constructor
	}
	
	/**
	 * @return search user query
	 */
	public String getSearchUser() {
		return searchUser;
	}
	/**
	 * set search query string
	 * @param searchUser - criteria to search the user details
	 */
	public void setSearchUser(final String searchUser) {
		this.searchUser = searchUser;
	}
	/**
	 * @return hibernate sessionfactory object
	 */
	public SessionFactory getFactory() {
		return factory;
	}
	/**
	 * set hibernate session factory object
	 * @param factory - session factory
	 */
	public void setFactory(final SessionFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * @return the digester for password encryption
	 */
	public PooledStringDigester getStringDigester() {
		return stringDigester;
	}
	
	/**
	 * set the password ecryption digestor
	 * @param stringDigester - PooledStringDigester
	 */
	public void setStringDigester(final PooledStringDigester stringDigester) {
		this.stringDigester = stringDigester;
	}
	
	/**
	 * @return valid license configured in the configuration system
	 */
	public String getDefltLicense() {
		return defltLicense;
	}
	
	/**
	 * set valid license to the IAM configuration module
	 * @param defltLicense - valid license
	 */
	public void setDefltLicense(final String defltLicense) {
		this.defltLicense = defltLicense;
	}
	
	/**
	 * Add new user to the USER table. Using Pooled string digest to 
	 * ecnrypt the password
	 * @param user - user object
	 */
	@Override
	public void addUser(final Credentail user) {
		try
		{
			//ading encryption to the password
			final String passwrdDigest = stringDigester.digest(user.getPassword());
			user.setPassword(passwrdDigest);
			final Session session = factory.openSession();
			final Transaction trans = session.beginTransaction();
			session.saveOrUpdate(user);
			trans.commit();
			session.close();
		}
		catch(Exception exp)
		{
			LogManager.log(exp.getMessage(), this.getClass(),Level.ERROR);
		}
	}
	/**
	 * Check whether User exist or not in USER table
	 * @param user - user object
	 * @return return true if user is valid otherwise false
	 */
	@Override
	public boolean checkUserAuthentication(final Credentail user) {
		// TODO Auto-generated method stub
		boolean bFndUser =  false;
		try
		{
			List<Credentail> userLists=null;
			final Session session = factory.openSession();
			final Transaction trans = session.beginTransaction();
			final Query query = session.createQuery(searchUser);
			query.setString("username", user.getUsername());
			userLists = query.list();
			for (final Credentail userObj : userLists) {
				if( stringDigester.matches(user.getPassword(), userObj.getPassword()))
				{
					bFndUser = true;
					break;
				}
			}
			trans.commit();
			session.close();
		}
		catch(Exception exp)
		{
			LogManager.log(exp.getMessage(), this.getClass(),Level.ERROR);
		}
		return bFndUser;
	}
	
	/**
	 * Check whether the User corresponds to existing license exists of not
	 * @param license - valid license
	 * @return true if user already registered with license otherwise false
	 */
	@Override
	public boolean licensedUserAlreadyExist(final String license) {
		boolean bFndUser =  false;
		try
		{
			List<Credentail> userLists;
			final Session session = factory.openSession();
			final Transaction trans = session.beginTransaction();
			final Query query = session.createQuery("from Credentail where license = :license");
			query.setString("license", license);
			userLists = query.list();
			for (final Credentail userObj : userLists) {
				if( userObj.getLicense().equalsIgnoreCase(license))
				{
					bFndUser = true;
					break;
				}
			}
			trans.commit();
			session.close();
		}
		catch(Exception exp)
		{
			LogManager.log(exp.getMessage(), this.getClass(),Level.ERROR);
		}
		
		return bFndUser;
	}
	
	/** 
	 * checks whether the license is valid or not
	 * @param license - valid license 
	 * @return true if the license is valid other wise false
	 */
	@Override
	public boolean checkValidLicense(final String license) {
		// TODO Auto-generated method stub
		return defltLicense.equalsIgnoreCase(license);
	}
}
