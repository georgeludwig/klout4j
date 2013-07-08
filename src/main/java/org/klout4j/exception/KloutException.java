package org.klout4j.exception;

/**
 * 
 * The root exception for errors encountered while communicating with the Klout API.
 *
 * @see org.klout4j.service.impl.KloutImpl
 */
public class KloutException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message.
	 * @param message Error message
	 */
    public KloutException(String message) {
        super(message);
    }

    /**
     * Constructor with message and root cause.
     * @param message Error message
     * @param t Throwable up the stack
     */
    public KloutException(String message, Throwable t) {
        super(message, t);
    }
}
