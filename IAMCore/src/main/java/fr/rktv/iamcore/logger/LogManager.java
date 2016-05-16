package fr.rktv.iamcore.logger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * The class handling logging of all the modules
 * @author Rahul Thai Valappil
 * @version 1.0
 */
final public class LogManager {
	
	/**
	 * private constructor
	 */
	private LogManager() {
	        // Prevent instantiation
		}
	
	/**
	 * log messages or errors to the log file
	 * @param message - message to log
	 * @param className - information about class from where the log started
	 * @param level - Log level like Error Debug info etc
	 */
	public static void log(final String message, final Class<?> className,final Level level ){
		final Logger loggerObj = Logger.getLogger(className);
		loggerObj.log(level, message);
    }
}
