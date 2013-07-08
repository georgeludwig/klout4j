package org.klout4j.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.klout4j.exception.KloutException;
import org.klout4j.exception.ProfileNotFoundException;
import org.klout4j.model.*;
import org.klout4j.service.Klout;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
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

    @SuppressWarnings("unchecked")
    public Long getKloutId(Long twitterId) throws KloutException {
        Object response = callJSON(getIdentityRequestString(IdentityType.twitterId, twitterId));
        if (response != null && response instanceof Map) {
            Map<String, String> map = (Map<String, String>) response;
            if (map.containsKey("id")) {
                return Long.parseLong(map.get("id"));
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Long getKloutId(String twitterScreenName) throws KloutException {
        Object response = callJSON(getIdentityRequestString(IdentityType.twitterScreenName, twitterScreenName));
        if (response != null && response instanceof Map) {
            Map<String, String> map = (Map<String, String>) response;
            if (map.containsKey("id")) {
                return Long.parseLong(map.get("id"));
            }
        }
        return null;
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
    @SuppressWarnings("unchecked")
    public KloutUser showUser(Long kloutId)
            throws ProfileNotFoundException, KloutException {
        Map<String, Object> data = (Map<String, Object>) callJSON(getRequestString(RequestType.SHOW_USER, kloutId));
        return new KloutUser(data);
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
    @SuppressWarnings("unchecked")
    public Topics topics(Long kloutId) throws ProfileNotFoundException, KloutException {
        JSONArray data = (JSONArray) callJSON(getRequestString(RequestType.TOPICS, kloutId));
        return new Topics(data);
    }

    @SuppressWarnings("unchecked")
    public KloutScore kloutScore(Long kloutId) throws KloutException {
        Map<String, Object> data = (Map<String, Object>) callJSON(getRequestString(RequestType.KLOUT_SCORE, kloutId));
        return new KloutScore(data);
    }

    private Object callJSON(String request) throws KloutException {
        URL url;
        try {
            url = new URL(request);
        } catch (java.net.MalformedURLException e) {
            throw new KloutException("Failed to construct URL", e);
        }
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Hitting URL: " + request);
        }
        HttpURLConnection conn;
        try {
            conn = getHttpURLConnection(url, "GET", null, false);
            if (conn.getResponseCode() != 200) {
                throw new KloutException("Failed to get request \""
                        + request + "\", HTTP status: "
                        + conn.getResponseCode() + " "
                        + conn.getResponseMessage());
            }
        } catch (java.io.IOException e) {
            throw new KloutException("Failed to open connection", e);
        }

        Object attributes;
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
            attributes = new JSONParser().parse(new InputStreamReader(inputStream));
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
        // TODO error handling
        return attributes;
    }

    /**
     * Build a query string to get klout info
     *
     * @param requestType Type of sent request
     * @param id          Klout ID
     * @return Request string
     */
    protected String getRequestString(RequestType requestType, Long id) {
        // build user list string
        // add user list and api key
        StringBuilder sb = new StringBuilder(rootUrl);
        String request = requestType.getPath(id);
        sb.append(request);
        sb.append("?key=").append(apiKey);
        return sb.toString();
    }

    /**
     * Build a query string to get Klout ID
     *
     * @param identityType Type of an identity request is sent with
     * @param id           Identity itself (numeric or name)
     * @return Request string
     */
    protected String getIdentityRequestString(IdentityType identityType,
                                              Object id) {
        StringBuilder sb = new StringBuilder(rootUrl);
        String request = identityType.getPath(id);
        sb.append(request);
        sb.append("key=").append(apiKey);
        return sb.toString();
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
