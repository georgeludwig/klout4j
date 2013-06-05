package org.klout4j;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.JSONParser;

/**
 * 
 * A connection class that will retrieve a KloutUser, or list of type KloutUser,
 * from Klout.
 * 
 * @author George
 * @see KloutUser
 */
public class Klout {
	
	public static final String USERAGENT = "klout4j/1.0.0";
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * Empty constructor. You will need to set the API key before using.
	 */
	public Klout() {}

	/**
	 * Constructor with API Key.
	 * 
	 * @param apiKey
	 */
	public Klout(String apiKey) {
		setApiKey(apiKey);
	}
	
	private String apiKey;
	
	/**
	 * Sets the API Key
	 * 
	 * @param apiKey
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	private String rootUrl = "http://api.klout.com";
	
	/**
	 * Returns the root URL of tke Klout API
	 * 
	 * @return
	 */
	public String getRootUrl() {
		return rootUrl;
	}

	/**
	 * Sets the root URL of the Klout API. Changing root URL is allowed
	 * in case Klout changes it, Klout4J is still usable until it gets updated.
	 * 
	 * @param rootUrl
	 */
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	private int connectTimeout = -1;
	
	/**
	 * Returns the connectiom timeout for accessing the Klout API. The default
	 * is no timeout.
	 * 
	 * @return
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * Sets the connection timeout for accessing the Klout API.
	 * 
	 * @param connectTimeout
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
	 * @param connectTimeout
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	/**
	 * Returns the KloutScore for the twitter user <p>
	 * Throws ProfileNotFoundException if the twitter screen name is not found
	 * @param twitterScreenName
	 * @return KloutScore
	 * @throws ProfileNotFoundException
	 * @throws KloutException
	 */
	public KloutScore kloutScore(String twitterScreenName) 	throws ProfileNotFoundException, KloutException {
		List<String> twitterScreenNames = new ArrayList<String>();
		twitterScreenNames.add(twitterScreenName);
		Map<String, KloutScore> users=kloutScore(twitterScreenNames);
		if (users.isEmpty()) {
			throw new ProfileNotFoundException(twitterScreenName);
		} else {
			return users.entrySet().iterator().next().getValue();
		}
	}

	/**
	 * returns a map of KloutScore keyed by twitter screen name<p>
	 * Throws ProfileNotFoundException if any of the twitter screen names are not found
	 * @param twitterScreenNames
	 * @return KloutScore
	 * @throws ProfileNotFoundException
	 * @throws KloutException
	 */
	public Map<String,KloutScore> kloutScore(List<String> twitterScreenNames) throws ProfileNotFoundException, KloutException {
		logger.fine("Topics for users: " + twitterScreenNames);
		Map<String, KloutScore> kloutScoreByTwitterScreenName = new LinkedHashMap<String, KloutScore>();
		List<Map<String, Object>>userAttrList=callJSON(RequestType.KLOUT_SCORE,twitterScreenNames);
		if (userAttrList != null) {
			for (Map<String, Object> userAttrs : userAttrList) {
				KloutScore kloutScore=new KloutScore(userAttrs);
				kloutScoreByTwitterScreenName.put(kloutScore.getTwitterScreenName(),kloutScore);
			}
		}
		return kloutScoreByTwitterScreenName;
	}
	
	/**
	 * Returns a KoutUser corresponding to the Twitter Username.<p>
	 * Throws ProfileNotFoundException if the call succeeds but does not return
	 * a KloutUser.
	 * 
	 * @param twitterScreenName
	 * @return KloutUser
	 * @throws ProfileNotFoundException
	 * @throws KloutException
	 */
	public KloutUser showUser(String twitterScreenName)
			throws ProfileNotFoundException, KloutException {
		List<String> twitterScreenNames = new ArrayList<String>();
		twitterScreenNames.add(twitterScreenName);
		Map<String, KloutUser> users = showUsers(twitterScreenNames);
		if (users.isEmpty()) {
			throw new ProfileNotFoundException(twitterScreenName);
		} else {
			return users.entrySet().iterator().next().getValue();
		}
	}

	/**
	 * Returns a Map of KloutUser keyed on the twitter screen name.<p>
	 * Throws ProfileNotFoundException if any of the twitter screen names are
	 * not found.
	 * 
	 * @param twitterScreenNames
	 * @return Map<String, KloutUser>
	 * @throws ProfileNotFoundException
	 * @throws KloutException
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String, KloutUser> showUsers(List<String> twitterScreenNames)
			throws ProfileNotFoundException, KloutException {
		logger.fine("Showing users: " + twitterScreenNames);
		Map<String, KloutUser> usersByTwitterScreenName = new LinkedHashMap<String, KloutUser>();
		List<Map<String, Object>>userAttrList=callJSON(RequestType.SHOW_USER,twitterScreenNames);
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
	 * @param twitterScreenName
	 * @return Topics
	 * @throws ProfileNotFoundException
	 * @throws KloutException
	 */
	public Topics topics(String twitterScreenName) 	throws ProfileNotFoundException, KloutException {
		List<String> twitterScreenNames = new ArrayList<String>();
		twitterScreenNames.add(twitterScreenName);
		Map<String, Topics> users=topics(twitterScreenNames);
		if (users.isEmpty()) {
			throw new ProfileNotFoundException(twitterScreenName);
		} else {
			return users.entrySet().iterator().next().getValue();
		}
	}

	/**
	 * returns a Map of Topics keyed to twitter screen name. <p>
	 * Throws ProfileNotFoundException if any of the twitter screen names are not found
	 * @param twitterScreenNames
	 * @return Map<String,Topics>
	 * @throws ProfileNotFoundException
	 * @throws KloutException
	 */
	public Map<String,Topics> topics(List<String> twitterScreenNames) throws ProfileNotFoundException, KloutException {
		logger.fine("Topics for users: " + twitterScreenNames);
		Map<String, Topics> topicsByTwitterScreenName = new LinkedHashMap<String, Topics>();
		List<Map<String, Object>>userAttrList=callJSON(RequestType.TOPICS,twitterScreenNames);
		if (userAttrList != null) {
			for (Map<String, Object> userAttrs : userAttrList) {
				Topics topics=new Topics(userAttrs);
				topicsByTwitterScreenName.put(topics.getTwitterScreenName(), topics);
			}
		}
		return topicsByTwitterScreenName;
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>>callJSON(RequestType requestType,List<String> twitterScreenNames) throws KloutException {
		String urlString;
		URL url;
		try {
			urlString=getRequestString(requestType,twitterScreenNames);
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
						+ twitterScreenNames + "\", HTTP status: "
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
					+ twitterScreenNames + " not found, status=" + status
					+ " (" + attributes.get("status_message") + ")");
		}

		List<Map<String, Object>> userAttrList = (List<Map<String, Object>>) attributes
				.get("users");
		return userAttrList;
	}
	
	private String getRequestString(RequestType requestType, List<String> twitterScreenNames) throws UnsupportedEncodingException {
		StringBuilder sb=new StringBuilder();
		// add root url
		sb.append(rootUrl).append("/1/");
		// build user list string
		int count=0;
		StringBuilder sbu=new StringBuilder();
		for (String twitterScreenName : twitterScreenNames) {
			if (count++ > 0) {
				sbu.append(',');
			}
			try {
				sbu.append(URLEncoder.encode(twitterScreenName, "utf-8"));
			} catch (java.io.UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		// add request typw
		switch(requestType) {
			case KLOUT_SCORE:
				sb.append("klout.json?");
				break;
			case SHOW_USER:
				sb.append("users/show.json?");
				break;
			case TOPICS:
				sb.append("users/topics.json?");
				break;
			case INFLUENCED_BY:
				sb.append("soi/influenced_by.json?");
				break;
			case INFLUENCER_OF:
				sb.append("soi/influenced_of.json?");
				break;
			default: 
				// nothing to do here
		}
		// add user list and api key	
		sb.append("users=").append(sbu.toString()).append("&key=").append(URLEncoder.encode(apiKey, "utf-8"));
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
