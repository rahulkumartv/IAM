package fr.rktv.iamcore.test.hibernate;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import fr.rktv.iamcore.datamodel.Identity;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.rktv.iamcore.services.dao.IdentityDAOInterface;

/**
 * Hibernate DAo test class to test the functionalities
 * manipulate the database
 * @author Rahul Thai Valappil
 * @version 1.0
 *
 */
//This is to tell Junit to run with spring
@RunWith(SpringJUnit4ClassRunner.class) 
//to tell spring to load the required context
@ContextConfiguration(locations={"file:src/test/resources/application-context.xml"})
public class HibernateDAOTest {

	@Autowired
	@Qualifier("hibernateDAO")
	private IdentityDAOInterface dao;
	
	@Autowired
	@Qualifier("dataSourceBean")
	private DataSource datsource;
	
	@Autowired
    @Qualifier("beanBasedSessionFactory")
	private SessionFactory sessionFactory;

	@Test
	public void springSetup(){
		Assert.notNull(datsource);
	}
	
	/**
	 * Test derby data source setup
	 * @return void 
	 */
	@Test
	public void dataSourceUsage() throws SQLException{
		final Connection connection = datsource.getConnection();
		Assert.notNull(connection.getSchema());
		connection.close();

		
	}
	
	/**
	 * Test Hibernate setup
	 * @return void 
	 */
	@Test
	public void sessionFactoryUsage(){
		final Session session = sessionFactory.openSession();
		final Transaction transt = session.beginTransaction();
		session.saveOrUpdate(new Identity("Rahul","Kumar", "rahul@gmail.com"));
		transt.commit();
	}
	
	/**
	 * Test Hibernate DAo Write
	 * @return void 
	 */
	@Test
	public void hibernateDaoWrite(){
		dao.write(new Identity("SampleUser","Test", "sample@gmail.com"));
		
	}
	
	/**
	 * Test Hibernate DAo Search
	 * @return void 
	 */	
	@Test
	public void hibernateDaoSearch() throws IllegalArgumentException, IllegalAccessException{
		Identity identity = new Identity("Prince","Mathew", "Prince@gmail.com");
		dao.write(identity);
		identity = new Identity("Ragesh","T V", "ragesh@gmail.com");
		dao.write(identity);
		final List<Identity> searchResul =dao.search(identity);
		Assert.notEmpty(searchResul);
	}
	
	/**
	 * Test hibernate Dao ReadAll
	 * @return void 
	 */
	@Test
	public void hibernateDaoReadAll() throws IllegalArgumentException, IllegalAccessException{
		final Identity identity = new Identity("Naveen","P", "Naveen@gmail.com");
		dao.write(identity);
		final List<Identity> searchResul =dao.readAll();
		Assert.notEmpty(searchResul);
	}
	
	/**
	 * Test hibernate Dao Delete
	 * @return void 
	 */
	@Test
	public void hibernateDaoDelete() throws IllegalArgumentException, IllegalAccessException{
		final Identity identity = new Identity("DeleteUser","Test", "delete@gmail.com");
		dao.write(identity);
		List<Identity> searchResul =dao.search(identity);
		Assert.isTrue( !searchResul.isEmpty());		
		identity.setIdentId(searchResul.get(0).getIdentId());
		dao.delete(identity);
		searchResul =dao.search(identity);
		Assert.isTrue(searchResul.isEmpty());
	}
	
	/**
	 * Test hibernate Dao Update
	 * @return void 
	 */
	@Test
	public void hibernateDaoUpdate() throws IllegalArgumentException, IllegalAccessException{
		final Identity identity = new Identity("UpdateUser","Test", "update@gmail.com");
		dao.write(identity);
		List<Identity> searchResul =dao.search(identity);
		Assert.isTrue( !searchResul.isEmpty());
		identity.setIdentId(searchResul.get(0).getIdentId());
		dao.delete(identity);
		searchResul =dao.search(identity);
		Assert.isTrue(searchResul.isEmpty());
	}
}
