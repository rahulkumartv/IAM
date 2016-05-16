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

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.rktv.iamcore.datamodel.Identity;
import fr.rktv.iamcore.services.dao.impl.IdentityHibernateDAO;

/**
 * Servlet implementation class for Searching the Servlet
 * @author Rahul Thai Valappil
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet("/Search")
public class Search extends GeneralServlet {
	
	@Autowired
	@Qualifier("hibernateDAO")
	private IdentityHibernateDAO hibernateDAO;
	
	/**
	 * Set the Hibernate DAO object
	 * @param hibernateDAO - IdentityHibernateDAO
	 */
	public void setHibernateDAO(IdentityHibernateDAO hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}
	
	/**
	 * default constructor 
	 */
	public Search() {
		super();
		 // The explicit constructor is here, so that it is possible to provide Javadoc.
	    }
	/**
	 * @see Search#doGet(HttpServletRequest request, 
	 * HttpServletResponse response)
	 *  @param request -HttpServletRequest
	 *  @param response -HttpServletResponse
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// currently not implementing the GET method
	}

	/**
	 * @see Search#doPost(HttpServletRequest request,
	 *  HttpServletResponse response)
	 *  @param request -HttpServletRequest
	 *  @param response -HttpServletResponse
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		final PrintWriter out = response.getWriter();
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
				List<Identity> searchResult;
				searchResult= hibernateDAO.search(idObj);
				JSONArray jsonArray = new JSONArray();
				ObjectMapper mapper = new ObjectMapper();
				for (Identity identity : searchResult) {
					String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(identity);
					
					jsonArray.put(jsonInString);
				}
				JSONObject json = new JSONObject();
				json.put("SearchResults", jsonArray);
				out.print(json);
			}
			else
			{
				out.write("Error : Unauthorized access. Please login with valid credentail to search Identities");
			}	
			
		}
		catch(Exception exp )
		{
			out.write("Error while Searching the Identity Please contact Administator");
		}
		finally
		{
			out.flush();
			out.close();
		}
		
	}

}
