package org.klout4j.service.impl;

import org.json.simple.parser.JSONParser;
import org.klout4j.exception.KloutException;
import org.klout4j.exception.ProfileNotFoundException;
import org.klout4j.model.KloutScore;
import org.klout4j.model.KloutUser;
import org.klout4j.model.RequestType;
import org.klout4j.model.Topics;
import org.klout4j.service.Klout;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A connection class that will retrieve a KloutUser, or list of type KloutUser,
 * from Klout.
 *
 * @author George
 * @see org.klout4j.model.KloutUser
 */
public class KloutImpl implements Klout {

    public static final String USERAGENT = "klout4j/1.0.0";

    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Empty constructor. You will need to set the API key before using.
     */
    public KloutImpl() {
    }

    /**
     * Constructor with API Key.
     *
     * @param apiKey API Key for Klout
     */
    public KloutImpl(String apiKey) {
        setApiKey(apiKey);
    }

    private String apiKey;

    /**
     * Sets the API Key
     *
     * @param apiKey API Key for Klout
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private String rootUrl = "http://api.klout.com/v2/";

    /**
     * Returns the root URL of tke Klout API
     *
     * @return Root URL
     */
    public String getRootUrl() {
        return rootUrl;
    }

    /**
     * Sets the root URL of the Klout API. Changing root URL is allowed
     * in case Klout changes it, Klout4J is still usable until it gets updated.
     *
     * @param rootUrl Root URL
     */
    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    private int connectTimeout = -1;

    /**
     * Returns the connectiom timeout for accessing the Klout API. The default
     * is no timeout.
     *
     * @return Timeout
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets the connection timeout for accessing the Klout API.
     *
     * @param connectTimeout Timeout
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    private int readTimeout = -1;

    /**
     * Returns the read timeout for accessing the Klout API. The default is no
     * timeout.
     *
     * @return int
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * Sets the read timeout for accessing the Klout API.
     *
     * @param readTimeout Read Timeout
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Long getKloutId(Long twitterId) {
        // TODO
        return null;
    }

    public Long getKloutId(String twitterScreenName) {
        // TODO
        return null;
    }

    /**
     * Returns the KloutScore for the twitter user <p>
     * Throws ProfileNotFoundException if the twitter screen name is not found
     *
     * @param kloutId Klout ID
     * @return KloutScore Score object
     * @throws org.klout4j.exception.ProfileNotFoundException Profile Not Found Exception
     * @throws org.klout4j.exception.KloutException           Generic Klout Exception
     */
    public KloutScore kloutScore(Long kloutId) throws ProfileNotFoundException, KloutException {
        Map<String, KloutScore> users = kloutScore(Collections.singletonList(kloutId));
        if (users.isEmpty()) {
            throw new ProfileNotFoundException(kloutId);
        } else {
            return users.entrySet().iterator().next().getValue();
        }
    }

    /**
     * Returns a map of KloutScore keyed by twitter screen name<p>
     * Throws ProfileNotFoundException if any of the twitter screen names are not found
     *
     * @param kloutIds Klout IDS
     * @return KloutScore
     * @throws ProfileNotFoundException
     * @throws KloutException
     */
    public Map<String, KloutScore> kloutScore(List<Long> kloutIds) throws ProfileNotFoundException, KloutException {
        logger.fine("Topics for users: " + kloutIds);
        Map<String, KloutScore> kloutScoreByTwitterScreenName = new LinkedHashMap<String, KloutScore>();
        List<Map<String, Object>> userAttrList = callJSON(RequestType.KLOUT_SCORE, kloutIds);
        if (userAttrList != null) {
            for (Map<String, Object> userAttrs : userAttrList) {
                KloutScore kloutScore = new KloutScore(userAttrs);
                kloutScoreByTwitterScreenName.put(kloutScore.getTwitterScreenName(), kloutScore);
            }
        }
        return kloutScoreByTwitterScreenName;
    }

    /**
     * Returns a KloutUser corresponding to the Twitter Username.<p>
     * Throws ProfileNotFoundException if the call succeeds but does not return
     * a KloutUser.
     *
     * @param kloutId Klout ID
     * @return KloutUser
     * @throws ProfileNotFoundException
     * @throws KloutException
     */
    public KloutUser showUser(Long kloutId)
            throws ProfileNotFoundException, KloutException {
        Map<String, KloutUser> users = showUsers(Collections.singletonList(kloutId));
        if (users.isEmpty()) {
            throw new ProfileNotFoundException(kloutId);
        } else {
            return users.entrySet().iterator().next().getValue();
        }
    }

    /**
     * Returns a Map of KloutUser keyed on the twitter screen name.<p>
     * Throws ProfileNotFoundException if any of the twitter screen names are
     * not found.
     *
     * @param kloutIds Klout IDS
     * @return Map<String, KloutUser>
     * @throws ProfileNotFoundException
     * @throws KloutException
     * @throws ProfileNotFoundException
     */
    public Map<String, KloutUser> showUsers(List<Long> kloutIds)
            throws ProfileNotFoundException, KloutException {
        logger.fine("Showing users: " + kloutIds);
        Map<String, KloutUser> usersByTwitterScreenName = new LinkedHashMap<String, KloutUser>();
        List<Map<String, Object>> userAttrList = callJSON(RequestType.SHOW_USER, kloutIds);
        if (userAttrList != null) {
            for (Map<String, Object> userAttrs : userAttrList) {
                KloutUser user = new KloutUser(userAttrs);
                usersByTwitterScreenName.put(user.getTwitterScreenName(), user);
            }
        }
        return usersByTwitterScreenName;
    }

    /**
     * returns the Topics that the twitter user influences.<p>
     * Throws ProfileNotFoundException if the twitter screen name is not found
     *
     * @param kloutId Klout ID
     * @return Topics
     * @throws ProfileNotFoundException
     * @throws KloutException
     */
    public Topics topics(Long kloutId) throws ProfileNotFoundException, KloutException {
        Map<String, Topics> users = topics(Collections.singletonList(kloutId));
        if (users.isEmpty()) {
            throw new ProfileNotFoundException(kloutId);
        } else {
            return users.entrySet().iterator().next().getValue();
        }
    }

    /**
     * returns a Map of Topics keyed to twitter screen name. <p>
     * Throws ProfileNotFoundException if any of the twitter screen names are not found
     *
     * @param kloutIds Klout IDS
     * @return Map Map of  &lt;String,Topics&gt;
     * @throws ProfileNotFoundException
     * @throws KloutException
     */
    public Map<String, Topics> topics(List<Long> kloutIds) throws ProfileNotFoundException, KloutException {
        logger.fine("Topics for users: " + kloutIds);
        Map<String, Topics> topicsByTwitterScreenName = new LinkedHashMap<String, Topics>();
        List<Map<String, Object>> userAttrList = callJSON(RequestType.TOPICS, kloutIds);
        if (userAttrList != null) {
            for (Map<String, Object> userAttrs : userAttrList) {
                Topics topics = new Topics(userAttrs);
                topicsByTwitterScreenName.put(topics.getTwitterScreenName(), topics);
            }
        }
        return topicsByTwitterScreenName;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> callJSON(RequestType requestType, List<Long> kloutIds) throws KloutException {
        String urlString;
        URL url;
        try {
            urlString = getRequestString(requestType, kloutIds);
            url = new URL(urlString);
        } catch (java.net.MalformedURLException e) {
            throw new KloutException("Failed to construct URL", e);
        } catch (UnsupportedEncodingException ue) {
            throw new KloutException("Failed to construct URL string", ue);
        }
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Hitting URL: " + urlString);
        }
        HttpURLConnection conn;
        try {
            conn = getHttpURLConnection(url, "GET", null, false);
            if (conn.getResponseCode() != 200) {
                throw new KloutException("Failed to get profile detail for \""
                        + kloutIds + "\", HTTP status: "
                        + conn.getResponseCode() + " "
                        + conn.getResponseMessage());
            }
        } catch (java.io.IOException e) {
            throw new KloutException("Failed to open connection", e);
        }

        Map<String, Object> attributes;
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
            attributes = (Map<String, Object>) new JSONParser()
                    .parse(new InputStreamReader(inputStream));
        } catch (Exception e) {
            throw new KloutException("Failed to read/parse response", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    logger.log(Level.SEVERE,
                            "Failed to close HTTP InputStream", e);
                }
            }
        }

        Object status = attributes.get("status");
        if (status == null) {
            throw new KloutException("No status element found");
        } else if (!"200".equals(status.toString())) {
            throw new ProfileNotFoundException("Profile for "
                    + kloutIds + " not found, status=" + status
                    + " (" + attributes.get("status_message") + ")");
        }

        return (List<Map<String, Object>>) attributes.get("users");
    }

    private String getRequestString(RequestType requestType, List<Long> ids) throws UnsupportedEncodingException {
        // TODO
        // build user list string
        // add user list and api key
        return null;
    }


    private HttpURLConnection getHttpURLConnection(URL url,
                                                   String requestMethod, Map<String, String> headers, boolean doOutput)
            throws java.io.IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (connectTimeout != -1) {
            conn.setConnectTimeout(connectTimeout);
        }
        if (readTimeout != -1) {
            conn.setReadTimeout(readTimeout);
        }
        conn.setAllowUserInteraction(false);
        conn.setRequestMethod(requestMethod);
        conn.setDoInput(true);
        conn.setDoOutput(doOutput);
        conn.setRequestProperty("Connection", "close");
        conn.setRequestProperty("User-Agent", USERAGENT);
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }
        return conn;
    }
}
