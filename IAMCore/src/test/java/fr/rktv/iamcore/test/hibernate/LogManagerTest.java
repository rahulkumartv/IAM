package fr.rktv.iamcore.test.hibernate;
import org.apache.log4j.Level;
import org.junit.Test;

import fr.rktv.iamcore.logger.LogManager;


/**
 * Class to Test the functionalities of LogManger class using JUnit
 * @author Rahul Thai Valappil
 * @version 1.0
 */
public class LogManagerTest {
	
	/**
	 * Test case for Log Method
	 */
	@Test
	public void logAllLevelTest(){
		LogManager.log("Test All level Log", this.getClass(),Level.ALL);
	}
	
	/**
	 * Test case for Log Method
	 */
	@Test
	public void logDebugLevelTest(){
		LogManager.log("Test Debug level Log", this.getClass(),Level.DEBUG);
	}
	
	/**
	 * Test case for Log Method
	 */
	@Test
	public void logErrorLevelTest(){
		LogManager.log("Test Error level Log", this.getClass(),Level.ERROR);
	}
	
	/**
	 * Test case for Log Method
	 */
	@Test
	public void logFatalLevelTest(){
		LogManager.log("Test Fatal level Log", this.getClass(),Level.FATAL);
	}
	
	/**
	 * Test case for Log Method
	 */
	@Test
	public void logInfoLevelTest(){
		LogManager.log("Test Info level Log", this.getClass(),Level.INFO);
	}
	
	/**
	 * Test case for Log Method
	 */
	@Test
	public void logOffLevelTest(){
		LogManager.log("Test Off Level Log", this.getClass(),Level.OFF);
	}
	
	/**
	 * Test case for Log Method
	 */
	@Test
	public void logTraceLevelTest(){
		LogManager.log("Test Trace Level Log", this.getClass(),Level.TRACE);
	}
	
	/**
	 * Test case for Log Method
	 */
	@Test
	public void logWarnLevelTest(){
		LogManager.log("Test Warn Level log", this.getClass(),Level.WARN);
	}
}
