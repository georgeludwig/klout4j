package org.klout4j.model;

import java.text.MessageFormat;

/**
 * An enum indicating what type of JSON call is to be made
 *
 * @author George
 * @see org.klout4j.service.impl.KloutImpl
 */
public enum RequestType {
    SHOW_USER("user.json/{0}"),
    KLOUT_SCORE("/user.json/{0}/score"),
    TOPICS("/user.json/{0}/topics"),
    INFLUENCE("/user.json/{0}/influence");

    private String path;

    private RequestType(String path) {
        this.path = path;
    }

    public String getPath(Long id) {
        return MessageFormat.format(path, id.toString());
    }
}
