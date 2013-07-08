package org.klout4j.model;

import java.text.MessageFormat;

/**
 * Identity used to get Klout ID
 * Has a path used to form the request (only need to add the key={key})
 * @author Marina Chernyavskaya
 */
public enum IdentityType {
    twitterId("identity.json/tw/{0}?"),
    twitterScreenName("identity.json/twitter?screenName={0}&"),
    googlePlus("identity.json/gp/{0}?"),
    instagram("identity.json/ig/{0}?"),
    kloutId("identity.json/klout/{0}/tw?");

    private String path;

    private IdentityType(String path) {
        this.path = path;
    }

    public String getPath(Object id) {
        return MessageFormat.format(path, id.toString());
    }
}
