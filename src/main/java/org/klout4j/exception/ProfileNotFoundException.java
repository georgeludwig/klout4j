package org.klout4j.exception;

/**
 * The exception that is thrown when a user is not found.
 */
public class ProfileNotFoundException extends KloutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor with message.
     *
     * @param message Error
     */
    public ProfileNotFoundException(String message) {
        super(message);
    }

    public ProfileNotFoundException(Long kloutId) {
        super("Klout profile not found: " + kloutId);
    }
}
