package org.klout4j.exception;

/**
 * The exception that is thrown when a Klout ID is not found.
 * Klout ID can be found using several methods (@link org.klout4j.model.IdentityType)
 * Every other kind of info (Klout score, Topics etc.) requires Klout ID.
 * Klout IDs are not subject to change and can be stored indefinitely. It is advised to do so if you make a lot
 * of Klout API calls.
 */
public class IdentityNotFoundException extends KloutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor with message.
     *
     * @param message Error
     */
    public IdentityNotFoundException(String message) {
        super(message);
    }

    public IdentityNotFoundException(Object identity) {
        super("Klout ID not found by identity: " + identity);
    }
}
