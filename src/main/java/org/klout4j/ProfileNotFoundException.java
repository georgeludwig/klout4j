package org.klout4j;

/**
 * The exception that is thrown when a user is not found.
 *
 */
public class ProfileNotFoundException extends KloutException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message.
	 * @param message
	 */
	ProfileNotFoundException(String message) {
        super(message);
    }
}
