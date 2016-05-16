package fr.rktv.iamweb.test.servlets;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import fr.rktv.iamcore.services.dao.impl.IdentityHibernateDAO;
import fr.rktv.iamweb.services.servlets.Update;

/**
 * Test Class responsible for testing the Update Servlet functionalities like POST and GET
 * Test case wont test the functionalities inside Hibernate and Authenticate DAO 
 * because those functionalities are already Tested in IamCore Unit Test case.So Those
 * DAO's are Mocked by using Mockito
 * @author Rahul Thai Valappil
 * @version 1.0
 */
public class UpdateHttpServletTest {
	private Update servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private HttpSession session;
    private IdentityHibernateDAO hibernateDAO;
    
    /**
     * Responsible for Setting up mock object to Test Servlet 
     */
    @Before
    public void setUp() {
        servlet = new Update();
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
     * Test Update Servlet Post method .To check whether the POST method handles 
     * Updation of Identity successfully
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void updateServletPostTestSucc() throws ServletException, IOException {
    	final String datapair = "{\"firstName\": \"Rahul\" , \"lastName\": \"T V\", \"email\": \"sample@gmail.com\", \"birthDate\": \"1987-05-05\"}";
    	request.setContent(datapair.getBytes());
    	Mockito.when(session.getAttribute("logon.isDone")).thenReturn(true);
    	Mockito.ignoreStubs(hibernateDAO);
        servlet.doPost(request, response);
        assertEquals("text/html", response.getContentType());
        assertEquals("Identity Succesfully Modified", response.getContentAsString());
    }
    
    /**
     * Test Update Servlet Post method .To check whether the POST method handles 
     * Error during the Identity Updation.example try to Update Identity with out 
     * any identity information
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void updateServletPostTestFail() throws ServletException, IOException {
    	Mockito.when(session.getAttribute("logon.isDone")).thenReturn(true);
    	Mockito.ignoreStubs(hibernateDAO);
        servlet.doPost(request, response);
        assertEquals("Error while updating the Identity Please contact Administator", response.getContentAsString());
    }
    
    /**
     * Test Update Servlet Post method .To check whether the POST method handles 
     * Error during the Identity Updation.example try to Update Identity with out 
     * valid credential.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void updateServletPostTestUnAuthAcess() throws ServletException, IOException {
    	final String datapair = "{\"firstName\": \"Rahul\" , \"lastName\": \"T V\", \"email\": \"sample@gmail.com\", \"birthDate\": \"1987-05-05\"}";
    	request.setContent(datapair.getBytes());
    	Mockito.when(session.getAttribute("logon.isDone")).thenReturn(false);
    	Mockito.ignoreStubs(hibernateDAO);
    	//Mockito.when(request.getSession()).thenReturn(session);
        servlet.doPost(request, response);
        assertEquals("text/html", response.getContentType());
        assertEquals("Error : Unauthorized access. Not permission to Update Identity", response.getContentAsString());
    }

}
