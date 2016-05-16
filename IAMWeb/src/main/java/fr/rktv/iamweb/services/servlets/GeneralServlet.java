package fr.rktv.iamweb.services.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * A Servlet abstract class derived from HttpServlet to handle the 
 * initialization of Spring context
 * @author Rahul Thai Valappil
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GeneralServlet extends HttpServlet {

	/**
	 * default constructor
	 */
	public GeneralServlet()
	{
		super();
		// nothing to initialize in constructor
	}
	/** 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init( final ServletConfig config ) throws ServletException
	{
		super.init(config);
		//Initialize Spring application context
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	

}
