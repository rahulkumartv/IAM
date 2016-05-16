package fr.rktv.iamweb.test.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.rktv.iamweb.services.servlets.Create;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.Assert.assertEquals;

import fr.rktv.iamcore.services.dao.impl.IdentityHibernateDAO;

/**
 * Test Class responsible for testing the Create Servlet functionalities like POST and GET
 * Test case wont test the functionalities inside Hibernate and Authenticate DAO 
 * becuase those functionalities are already Tested in IamCore Unit Test case.So Those
 * DAO's are Mocked by using Mockito
 * @author Rahul Thai Valappil
 * @version 1.0
 *
 */
public class CreateHttpServletsTest {
	
	private Create servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private HttpSession session;
    private IdentityHibernateDAO hibernateDAO;
    
    /**
     * Responsible for Setting up mock object to Test Servlet 
     */
    @Before
    public void setUp() {
        servlet = new Create();
        request = new MockHttpServletRequest();
        request.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        session = Mockito.mock(HttpSession.class);
        //session.setAttribute("logon.isDone", true);
        hibernateDAO = Mockito.mock(IdentityHibernateDAO.class);
        request.setSession(session);
        
        response = new MockHttpServletResponse();
        servlet.setHibernateDAO(hibernateDAO);
    }
    
    /**
     * Test Create Servlet Post method .To check whether the POST method handles 
     * Creation of Identity successfully
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createServletPostTestSucc() throws ServletException, IOException {
    	final String datapair = "{\"firstName\": \"Rahul\" , \"lastName\": \"T V\", \"email\": \"sample@gmail.com\", \"birthDate\": \"1987-05-05\"}";
    	request.setContent(datapair.getBytes());
    	Mockito.when(session.getAttribute("logon.isDone")).thenReturn(true);
    	Mockito.ignoreStubs(hibernateDAO);
        servlet.doPost(request, response);
        assertEquals("text/html", response.getContentType());
        assertEquals("Identity Succesfully Created", response.getContentAsString());
    }
    
    /**
     * Test Create Servlet Post method .To check whether the POST method handles 
     * Error during the Identity Creation.example try to create Identity with out 
     * any identity information
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createServletPostTestFail() throws ServletException, IOException {
    	Mockito.when(session.getAttribute("logon.isDone")).thenReturn(true);
    	Mockito.ignoreStubs(hibernateDAO);
        servlet.doPost(request, response);
        assertEquals("Error while creating Identity Please contact Administator", response.getContentAsString());
    }
    
    /**
     * Test Create Servlet Post method .To check whether the POST method handles 
     * Error during the Identity Creation.example try to create Identity with out 
     * valid credential.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createServletPostTestUnAuthAcess() throws ServletException, IOException {
    	final String datapair = "{\"firstName\": \"Rahul\" , \"lastName\": \"T V\", \"email\": \"sample@gmail.com\", \"birthDate\": \"1987-05-05\"}";
    	request.setContent(datapair.getBytes());
    	Mockito.when(session.getAttribute("logon.isDone")).thenReturn(false);
    	Mockito.ignoreStubs(hibernateDAO);
    	//Mockito.when(request.getSession()).thenReturn(session);
        servlet.doPost(request, response);
        assertEquals("text/html", response.getContentType());
        assertEquals("Error : Unauthorized access. Please login with valid credentail to create an Identity", response.getContentAsString());
    }

}
