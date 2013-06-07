package org.klout4j.model;

import java.util.Map;

/**
 * A class representing the Klout score of a Twitter user.
 * <p>
 * For more info see http://developer.klout.com/page/Klout_API_Onesheet
 * @author George
 * @see KloutUser
 */
public class KloutScore implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public KloutScore(Map<String, Object> attributes) {
		twitterScreenName = (String) attributes.get("twitter_screen_name");
		kscore = Double.parseDouble(attributes.get("kscore").toString());
	}

	private String twitterScreenName;
	
	/**
     * returns the Twitter screen name of this KloutUser
     * @return
     */
    public String getTwitterScreenName() {
        return twitterScreenName;
    }
    
	private double kscore = 0;

	/**
	 * returns the Klout score as a double
	 * 
	 * @return
	 */
	public double getKscore() {
		return kscore;
	}
	
	/**
	 * returns a String representation of this score
	 */
	public String toString() {
		return "KloutScore[twitterScreenName=" + twitterScreenName +
            ",kscore=" + kscore +"]";
	}
}
