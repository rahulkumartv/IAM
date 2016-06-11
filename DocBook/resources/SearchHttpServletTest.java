package fr.rktv.iamweb.test.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.Matchers.*;
import fr.rktv.iamcore.datamodel.Identity;
import fr.rktv.iamcore.services.dao.impl.IdentityHibernateDAO;
import fr.rktv.iamweb.services.servlets.Search;
import static org.junit.Assert.*;
/**
 * Test Class responsible for testing the Search Servlet functionalities like POST and GET
 * Test case wont test the functionalities inside Hibernate and Authenticate DAO 
 * becuase those functionalities are already Tested in IamCore Unit Test case.So Those
 * DAO's are Mocked by using Mockito
 * @author Rahul Thai Valappil
 * @version 1.0
 */
public class SearchHttpServletTest {

	private Search servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private HttpSession session;
    private IdentityHibernateDAO hibernateDAO;
    
    /**
     * Responsible for Setting up mock object to Test Servlet 
     */
    @Before
    public void setUp() {
        servlet = new Search();
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
     * Test Search Servlet Post method .To check whether the POST method handles 
     * searching of Identity successfully
     * @throws ServletException
     * @throws IOException
     * @throws ParseException 
     */
    @Test
    public void searchServletPostTestSucc() throws ServletException, IOException, ParseException {
    	final String datapair = "{\"firstName\": \"Rahul\" , \"lastName\": \"T V\", \"email\": \"sample@gmail.com\", \"birthDate\": \"1987-05-05\"}";
    	request.setContent(datapair.getBytes());
    	Identity ident = new Identity("Rahul","T V","sample@gmail.com");
    	Mockito.when(session.getAttribute("logon.isDone")).thenReturn(true);
    	List<Identity> results = new ArrayList<Identity>();
    	results.add(ident);
    	Mockito.when(hibernateDAO.search( any(Identity.class))).thenReturn(results);
        servlet.doPost(request, response);
        assertEquals("application/json", response.getContentType());       
        assertTrue(!response.getContentAsString().isEmpty());
    }
    
    /**
     * Test Search Servlet Post method .To check whether the POST method handles 
     * Error during the Identity search.example try to Search Identity with out 
     * any identity information
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void searchServletPostTestFail() throws ServletException, IOException {
    	Mockito.when(session.getAttribute("logon.isDone")).thenReturn(true);
    	Mockito.ignoreStubs(hibernateDAO);
        servlet.doPost(request, response);
        assertEquals("Error while Searching the Identity Please contact Administator", response.getContentAsString());
    }
    
    /**
     * Test Search Servlet Post method .To check whether the POST method handles 
     * Error during the Identity Search.example try to Search Identity with out 
     * valid credential.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void searchServletPostTestUnAuthAcess() throws ServletException, IOException {
    	final String datapair = "{\"firstName\": \"Rahul\" , \"lastName\": \"T V\", \"email\": \"sample@gmail.com\", \"birthDate\": \"1987-05-05\"}";
    	request.setContent(datapair.getBytes());
    	Mockito.when(session.getAttribute("logon.isDone")).thenReturn(false);
    	Mockito.ignoreStubs(hibernateDAO);
        servlet.doPost(request, response);
        assertEquals("Error : Unauthorized access. Please login with valid credentail to search Identities", response.getContentAsString());
    }
}
