package fr.rktv.iamcore.services.dao.impl;


import java.util.List;

import org.apache.log4j.Level;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.rktv.iamcore.datamodel.Identity;
import fr.rktv.iamcore.logger.LogManager;
import fr.rktv.iamcore.services.dao.IdentityDAOInterface;



/**
 * DAO that implements the methods required to manipulate the identity creation,search,deletion,updtion etc
 * using Hibernate and JDBC database. DAO will save the Identity  details to Derby database
 * @author Rahul Thai Valappil
 * @version 1.0
 *
 */
public class IdentityHibernateDAO implements IdentityDAOInterface{

	@Autowired
	@Qualifier("searchFromEmail")
	private String selectIdenityFromEmail;

	@Autowired
	@Qualifier("beanBasedSessionFactory")
	private SessionFactory factory;

	/**
	 * @return the identity search query
	 */
	public String getSelectIdenityFromEmail() {
		return selectIdenityFromEmail;
	}

	/**
	 * set the seach query for identity
	 * @param selectIdenityFromEmail - Search Criteria  for Identity search
	 */
	public void setSelectIdenityFromEmail(String selectIdenityFromEmail) {
		this.selectIdenityFromEmail = selectIdenityFromEmail;
	}
	
	/**
	 * @return the hibernate session factory object configured for the identity
	 */
	public SessionFactory getFactory() {
		return factory;
	}

	/**
	 * set hibernate session factory object for identity 
	 * @param factory -SessionFactory
	 */
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * read all the entries from Identity table
	 * @return List of Identity 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Identity> readAll() {
		List<Identity> identityList=null;	
		try
		{
			final Session session = factory.openSession();
			final Transaction trans = session.beginTransaction();
			identityList = session.createCriteria(Identity.class).list(); //queryBuilder.executeQuery();
			trans.commit();
			session.close();
		}
		catch(Exception exp)
		{
			LogManager.log(exp.getMessage(), this.getClass(),Level.ERROR);
		}
		return identityList;
	}

	/**
	 * search based on the Identity email address and return result
	 * @param identity - Identity object
	 * @return List of Identity
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Identity> search(Identity identity) {
		List<Identity> identities = null;
		try
		{
			if(identity.getEmail().equalsIgnoreCase("*"))
			{
				identities =  readAll();
			}
			else
			{
				final Session session = factory.openSession();
				final Transaction trans = session.beginTransaction();
				final Query query = session.createQuery(selectIdenityFromEmail);//problem with this spring loads bean in singleton if u want to change need to restart the application
				query.setString("email", identity.getEmail());
				identities = query.list();
				trans.commit();
				session.close();
			}
		}
		catch(Exception exp)
		{
			LogManager.log(exp.getMessage(), this.getClass(),Level.ERROR);
		}
		return identities;
	}

	/**
	 * write Identity object to the session and update to the table
	 * @param identity -Identity object
	 */
	@Override
	public void write(Identity identity) {
		try
		{
			final Session session = factory.openSession();
			final Transaction trans = session.beginTransaction();
			session.saveOrUpdate(identity);
			trans.commit();
			session.close();
		}
		catch(Exception exp)
		{
			LogManager.log(exp.getMessage(), this.getClass(),Level.ERROR);
		}
		
	}

	/**
	 * update Identity the details to session and update to the table
	 * @param identity - Identity object
	 */
	@Override
	public void update(Identity identity) {
		try
		{
			final Session session = factory.openSession();
			final Transaction trans = session.beginTransaction();
			//session.saveOrUpdate(identity);
			Identity idupdate = (Identity)session.load(Identity.class, identity.getIdentId());
			idupdate.setBirthDate(identity.getBirthDate());
			idupdate.setEmail(identity.getEmail());
			idupdate.setFirstName(identity.getFirstName());
			idupdate.setLastName(identity.getLastName());
			session.saveOrUpdate(idupdate);
			trans.commit();	
			session.close();
		}
		catch(Exception exp)
		{
			LogManager.log(exp.getMessage(), this.getClass(),Level.ERROR);
		}
	}

	/**
	 * delete Identity  details from  session and from table
	 * @param identity - identity object
	 */
	@Override
	public void delete(Identity identity) {
		try
		{
			final Session session = factory.openSession();
			final Transaction trans = session.beginTransaction();
			Identity newIdentity = (Identity)session.load(Identity.class, identity.getIdentId());
			session.delete(newIdentity);
			trans.commit();
			session.close();
		}
		catch(Exception exp)
		{
			LogManager.log(exp.getMessage(), this.getClass(),Level.ERROR);
		}
	}
}
