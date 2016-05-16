/**
 * 
 */
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

import fr.rktv.iamcore.datamodel.Identity;
import fr.rktv.iamcore.logger.LogManager;
import fr.rktv.iamcore.services.dao.impl.IdentityHibernateDAO;

/**
 * Servlet implementation class for Updating the identity
 * @author Rahul Thai Valappil
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet("/Update")
public class Update extends GeneralServlet {
	@Autowired
	@Qualifier("hibernateDAO")
	private IdentityHibernateDAO hibernateDAO;
	
	/**
	 * set the hibernate dao object
	 * @param hibernateDAO - DAO to communicate with hibernate
	 */
	public void setHibernateDAO(IdentityHibernateDAO hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}
	
	/**
	 * default constructor 
	 */
	public Update() {
		super();
		 // The explicit constructor is here, so that it is possible to provide Javadoc.
    }
	
	/**
	 * @see Update#doGet(HttpServletRequest request, 
	 * HttpServletResponse response)
	 *  @param request -HttpServletRequest
	 *  @param response -HttpServletResponse
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// currently not implementing the GET method
	}

	/**
	 * @see Update#doPost(HttpServletRequest request,
	 *  HttpServletResponse response)
	 *  @param request -HttpServletRequest
	 *  @param response -HttpServletResponse
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		final PrintWriter out = response.getWriter();
		String resString ="Identity Succesfully Modified";
		try
		{
			StringBuffer buffReq = new StringBuffer();
			String line = null;
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
			{
				buffReq.append(line);
			}
			
			final Identity idObj = new ObjectMapper().readValue(buffReq.toString(), Identity.class);
			final HttpSession session = request.getSession();
			final Object loginSts = session.getAttribute("logon.isDone");
			final boolean bloginSts = loginSts == null? false:(boolean)loginSts;
			if( bloginSts )
			{
				hibernateDAO.update(idObj);
			}
			else
			{
				resString ="Error : Unauthorized access. Not permission to Update Identity";
				LogManager.log("Unauthorized access. Not permission to Update Identity", this.getClass(), Level.ERROR);
			}	
			
			

		}
		catch(Exception exp )
		{
			resString = "Error while updating the Identity Please contact Administator";
			LogManager.log(exp.getMessage(), this.getClass(), Level.ERROR);
		}
		finally
		{
			out.write(resString);
			out.flush();
			out.close();
		}
		
	}

}
