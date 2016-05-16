package fr.rktv.iamweb.services.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.rktv.iamcore.datamodel.Identity;
import fr.rktv.iamcore.datamodel.Credentail;
import fr.rktv.iamcore.logger.LogManager;
import fr.rktv.iamcore.services.dao.AuthDAOInterface;
import fr.rktv.iamcore.services.dao.impl.IdentityHibernateDAO;


/**
 * Servlet implementation class for Authenticating the User
 * @author Rahul Thai Valappil
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet("/Login")
public class Authentication extends GeneralServlet{
	
	@Autowired
	@Qualifier("AuthenticationDAO")
	private AuthDAOInterface authenticationDAO;
	
	/**
	 * Set the Authenticate DAO object
	 * @param authenticationDAO - AuthDAOInterface
	 * 
	 */
	public void setAuthenticationDAO(AuthDAOInterface authenticationDAO) {
		this.authenticationDAO = authenticationDAO;
	}
	
	/**
	 * Default constructor. 
	 */
	public Authentication() {
		super();
		 // The explicit constructor is here, so that it is possible to provide Javadoc.
	    }
	/**
	 * @see Authentication#doGet(HttpServletRequest request, 
	 * HttpServletResponse response)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// currently not implementing the GET method
	}

	/**
	 * @see Authentication#doPost(HttpServletRequest request,
	 *  HttpServletResponse response)
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		final PrintWriter out = response.getWriter();
		String resString ="Succesfully Logged in";
		try
		{
			StringBuffer bufferReq = new StringBuffer();
			String readLine = null;
			BufferedReader reader = request.getReader();
			//read the User details and parse to its object
			while ((readLine = reader.readLine()) != null)
				bufferReq.append(readLine);
			
			final Credentail user = new ObjectMapper().readValue(bufferReq.toString(), Credentail.class);
			//check authentication
			if( authenticationDAO.checkUserAuthentication(user))
			{
				HttpSession session = request.getSession(true);
				session.setAttribute("logon.isDone", true);
			}
			else
			{
				resString ="Error in login : invalid usrename/password";
				LogManager.log("Error in login : invalid usrename/password" , this.getClass(), Level.ERROR);
			}

		}
		catch(Exception exp )
		{
			resString = "invalid usrename/password";
			LogManager.log(exp.getMessage() , this.getClass(), Level.ERROR);
		}
		finally
		{
			out.write(resString);
			out.flush();
			out.close();
		}
		
	}
}
