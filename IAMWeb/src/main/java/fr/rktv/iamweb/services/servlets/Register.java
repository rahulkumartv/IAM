package fr.rktv.iamweb.services.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.rktv.iamcore.datamodel.Credentail;
import fr.rktv.iamcore.logger.LogManager;
import fr.rktv.iamcore.services.dao.AuthDAOInterface;

/**
 * The Servlet class handling the User Register request
 * @author Rahul Thai Valappil
 * @version 1.0 
 */
@SuppressWarnings("serial")
@WebServlet("/Register")
public class Register extends GeneralServlet {

	@Autowired
	@Qualifier("AuthenticationDAO")
	private AuthDAOInterface authenticationDAO;
	
	/**
	 * Set the Authentication DAO object
	 * @param authenticationDAO - AuthDAOInterface
	 */
	public void setAuthenticationDAO(AuthDAOInterface authenticationDAO) {
		this.authenticationDAO = authenticationDAO;
	}
	
	/**
	 * default constructor
	 */
	public Register() {
		super();
		 // The explicit constructor is here, so that it is possible to provide Javadoc.
	    }
	/**
	 * @see Register#doGet(HttpServletRequest request,
	 *  HttpServletResponse response)
	 *  @param request - HttpServletRequest
	 *  @param response - HttpServletResponse
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// currently not implementing the GET method
	}

	/**
	 * @see Register#doPost(HttpServletRequest request, 
	 * HttpServletResponse response)
	 *  @param request -HttpServletRequest
	 *  @param response -HttpServletResponse
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String resString ="Succesfully Created Administrative Account in";
		try
		{
			StringBuffer buffReq = new StringBuffer();
			String line = null;
			BufferedReader reader = request.getReader();
			while(( line = reader.readLine()) != null )
				buffReq.append( line );
			
			Credentail user = new ObjectMapper().readValue( buffReq.toString(), Credentail.class );
			if( authenticationDAO.licensedUserAlreadyExist(user.getLicense() ) )
			{
				resString ="Licensed Account already Exists.Contact Admin for new License";
			}
			else
			{
				if(authenticationDAO.checkValidLicense(user.getLicense()))
				{
					authenticationDAO.addUser(user);
				}
				else
				{
					resString ="Invalid License.Contact Admin for correct License"; 
					LogManager.log("Invalid License.Contact Admin for correct License" , this.getClass(), Level.ERROR);
				}
			}
		}
		catch(Exception exp )
		{
			resString = "Error while creating Account. Contact Admin";
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
