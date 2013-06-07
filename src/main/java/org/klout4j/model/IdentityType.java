package org.klout4j.model;

import java.text.MessageFormat;

/**
 * Identity used to get Klout ID
 * Has a path used to form the request (only need to add the key={key})
 * @author Marina Chernyavskaya
 */
public enum IdentityType {
    twitterId("/tw/{0}?"),
    twitterScreenName("/twitter?screenName={0}&"),
    googlePlus("/gp/{0}?"),
    instagram("/ig/{0}?"),
    kloutId("klout/{0}/tw?");

    private String path;

    private IdentityType(String path) {
        this.path = path;
    }

    public String getPath(Object id) {
        return MessageFormat.format(path, id);
    }
}
