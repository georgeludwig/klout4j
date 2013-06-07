package org.klout4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

/**
 * A class representing the topics over which a user has influence. 
 * @author George
 *
 */
public class Topics implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public Topics(Map<String, Object> attributes) {
		twitterScreenName = (String)attributes.get("twitter_screen_name");
		JSONArray a=(JSONArray)attributes.get("topics");
		topics=new ArrayList<String>();
		topics.addAll(a);
	}
	
	private String twitterScreenName;
	
	/**
	 * returns the twitter screen name
	 * @return String
	 */
	public String getTwitterScreenName() {
		return twitterScreenName;
	}

	private List<String>topics;

	/**
	 * returns the topics this user influences
	 * @return List<String>
	 */
	public List<String> getTopics() {
		return topics;
	}
	
}
