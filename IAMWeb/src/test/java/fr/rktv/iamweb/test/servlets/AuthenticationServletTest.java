package fr.rktv.iamweb.test.servlets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import fr.rktv.iamcore.datamodel.Credentail;
import fr.rktv.iamcore.services.dao.AuthDAOInterface;
import fr.rktv.iamweb.services.servlets.Authentication;

/**
 * Test Class responsible for testing the Authentication Servlet functionalities like
 * POST and GET test case wont test the functionalities inside Hibernate and Authenticate DAO 
 * becuase those functionalities are already Tested in IamCore Unit Test case.
 * So Those DAO's are Mocked by using Mockito
 * @author Rahul Thai Valappil
 * @version 1.0
 */
public class AuthenticationServletTest {

	private Authentication servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private AuthDAOInterface authenticationDAO;
    
    /**
     * Responsible for Setting up mock object to Test Servlet 
     */
    @Before
    public void setUp() {
        servlet = new Authentication();
        request = new MockHttpServletRequest();
        request.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        //session.setAttribute("logon.isDone", true);
        authenticationDAO = Mockito.mock(AuthDAOInterface.class);        
        response = new MockHttpServletResponse();
        servlet.setAuthenticationDAO(authenticationDAO);
    }
    
    /**
     * Test Authentication Servlet Post method .To check whether the POST method handles 
     * Authentication successfully
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void authServletPostTestSucc() throws ServletException, IOException {
    	final String datapair = "{\"username\": \"test\" , \"password\": \"test\"}";
    	request.setContent(datapair.getBytes());
    	Mockito.when(authenticationDAO.checkUserAuthentication( any(Credentail.class))).thenReturn(true);
        servlet.doPost(request, response);
        assertEquals("Succesfully Logged in", response.getContentAsString());
        assertEquals(request.getSession().getAttribute("logon.isDone"), true);
    }
    
    /**
     * Test Authentication Servlet Post method .To check whether the POST method handles 
     * Error during the Authentication checking.example try to Authenticate with out 
     * passing user name and password
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void authServletPostTestFail() throws ServletException, IOException {
    	Mockito.ignoreStubs(authenticationDAO);
        servlet.doPost(request, response);
        assertEquals("invalid usrename/password", response.getContentAsString());
        assertEquals(request.getSession(false), null);
    }
    
    /**
     * Test Create Servlet Post method .To check whether the POST method handles 
     * Error during the Identity Creation.example try to create Identity with out 
     * valid credential.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void authServletPostTestUnAuthAcess() throws ServletException, IOException {
    	final String datapair = "{\"username\": \"test\" , \"password\": \"test\"}";
    	request.setContent(datapair.getBytes());
    	Mockito.when(authenticationDAO.checkUserAuthentication( any(Credentail.class))).thenReturn(false);
        servlet.doPost(request, response);
        assertEquals("Error in login : invalid usrename/password", response.getContentAsString());
        assertEquals(request.getSession(false), null);
    }
}
