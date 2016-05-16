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
import fr.rktv.iamweb.services.servlets.Register;

/**
 * Test Class responsible for testing the Registering new user Servlet functionalities like POST and GET
 * Test case wont test the functionalities inside Hibernate and Authenticate DAO 
 * because those functionalities are already Tested in IamCore Unit Test case.So Those
 * DAO's are Mocked by using Mockito
 * @author Rahul Thai Valappil
 * @version 1.0
 */
public class RegisterServletTest {
	private Register servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private AuthDAOInterface authenticationDAO;
    
    /**
     * Responsible for Setting up mock object to Test Servlet 
     */
    @Before
    public void setUp() {
        servlet = new Register();
        request = new MockHttpServletRequest();
        request.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        authenticationDAO = Mockito.mock(AuthDAOInterface.class);        
        response = new MockHttpServletResponse();
        servlet.setAuthenticationDAO(authenticationDAO);
    }
    
    /**
     * Test Registration Servlet Post method .To check whether the POST method handles 
     * Authentication successfully
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void regServletPostTestSucc() throws ServletException, IOException {
    	final String datapair = "{\"username\": \"test\" , \"password\": \"test\"}";
    	request.setContent(datapair.getBytes());
    	Mockito.when(authenticationDAO.licensedUserAlreadyExist( any(String.class))).thenReturn(false);
    	Mockito.when(authenticationDAO.checkValidLicense( any(String.class))).thenReturn(true);
    	Mockito.doNothing().when(authenticationDAO).addUser( any(Credentail.class));
        servlet.doPost(request, response);
        assertEquals("Succesfully Created Administrative Account in", response.getContentAsString());
        
    }
    
    /**
     * Test Registration Servlet Post method .To check whether the POST method handles 
     * Error during the Registration checking.example try to Authenticate with out 
     * passing user name and password
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void regServletPostTestFail() throws ServletException, IOException {
    	Mockito.ignoreStubs(authenticationDAO);
        servlet.doPost(request, response);
        assertEquals("Error while creating Account. Contact Admin", response.getContentAsString());
    }
    
    /**
     * Test Registration Servlet Post method .To check whether the POST method handles 
     * Error during the Registration.example try to create account using invalid license with out 
     * valid credential.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void regServletPostTestInvLicense() throws ServletException, IOException {
    	final String datapair = "{\"username\": \"test\" , \"password\": \"test\"}";
    	request.setContent(datapair.getBytes());
    	Mockito.when(authenticationDAO.licensedUserAlreadyExist( any(String.class))).thenReturn(false);
    	Mockito.when(authenticationDAO.checkValidLicense( any(String.class))).thenReturn(false);
    	Mockito.doNothing().when(authenticationDAO).addUser( any(Credentail.class));
        servlet.doPost(request, response);
        assertEquals("Invalid License.Contact Admin for correct License", response.getContentAsString());
    }
    
    /**
     * Test Registration Servlet Post method .To check whether the POST method handles 
     * Error during the Registration.example user account already with licnese
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void regServletPostTestExistLicense() throws ServletException, IOException {
    	final String datapair = "{\"username\": \"test\" , \"password\": \"test\"}";
    	request.setContent(datapair.getBytes());
    	Mockito.when(authenticationDAO.licensedUserAlreadyExist( any(String.class))).thenReturn(true);
    	Mockito.when(authenticationDAO.checkValidLicense( any(String.class))).thenReturn(true);
    	Mockito.doNothing().when(authenticationDAO).addUser( any(Credentail.class));
        servlet.doPost(request, response);
        assertEquals("Licensed Account already Exists.Contact Admin for new License", response.getContentAsString());
    }
}
