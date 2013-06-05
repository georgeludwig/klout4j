package org.klout4j;

/**
 * 
 * The root exception for errors encountered while communicating with the Klout API.
 *
 * @see Klout
 */
public class KloutException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Empty constructor.
	 */
	KloutException() {}
    
	/**
	 * Constructor with message.
	 * @param message
	 */
    KloutException(String message) {
        super(message);
    }

    /**
     * Constructor with message and root cause.
     * @param message
     * @param t
     */
    KloutException(String message, Throwable t) {
        super(message, t);
    }
}
