package org.klout4j;

import java.util.Map;

/**
 * A class representing a Klout user. Contains details on the user's Klout score.
 * 
 * @author George
 * 
 */
public class KloutUser implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that takes a map of properties.
	 * 
	 * @param attributes
	 */
	@SuppressWarnings("unchecked")
	protected KloutUser(Map<String, Object> attributes) {
		twitterId = Long.parseLong(attributes.get("twitter_id").toString());
		twitterScreenName = (String) attributes.get("twitter_screen_name");
		Map<String, Object> scoreAttributes=(Map<String, Object>)attributes.get("score");
		kscore = Double.parseDouble(scoreAttributes.get("kscore").toString());
		kscoreDescription=(String) scoreAttributes.get("kscore_description");
		slope = Double.parseDouble(scoreAttributes.get("slope").toString());
		description = (String) scoreAttributes.get("description");
		kclassId = scoreAttributes.get("kclass_id").toString();
		kclass = (String) scoreAttributes.get("kclass");
		kclassDescription = (String) scoreAttributes.get("kclass_description");
		networkScore = Double.parseDouble(scoreAttributes.get("network_score")
				.toString());
		amplificationScore = Double.parseDouble(scoreAttributes.get(
				"amplification_score").toString());
		trueReach = Long.parseLong(scoreAttributes.get("true_reach").toString());
		delta1Day = Double.parseDouble(scoreAttributes.get("delta_1day").toString());
		delta5Day = Double.parseDouble(scoreAttributes.get("delta_5day").toString());
	}

	long twitterId;

	/**
	 * returns the Twitter id of this KloutUser
	 * 
	 * @return
	 */
	public long getTwitterId() {
		return twitterId;
	}

	String twitterScreenName;

	/**
	 * returns the Twitter screen name of this KloutUser
	 * 
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

	private double slope = 0;

	/**
	 * returns the score slope as a double
	 * 
	 * @return
	 */
	public double getSlope() {
		return slope;
	}

	private String description;

	/**
	 * returns the score description
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	private String kclassId;

	/**
	 * returns the kclass
	 * 
	 * @return
	 */
	public String getKclassId() {
		return kclassId;
	}

	private String kclass;

	/**
	 * returns the kclass
	 * 
	 * @return
	 */
	public String getKclass() {
		return kclass;
	}

	private String kclassDescription;

	/**
	 * returns the kclass description
	 * 
	 * @return
	 */
	public String getKclassDescription() {
		return kclassDescription;
	}

	private String kscoreDescription;

	/**
	 * returns the kscore description
	 * 
	 * @return
	 */
	public String getKscoreDescription() {
		return kscoreDescription;
	}

	double networkScore = 0;

	/**
	 * returns the network score
	 * 
	 * @return
	 */
	public double getNetworkScore() {
		return networkScore;
	}

	private double amplificationScore = 0;

	/**
	 * returns the amplification score
	 * 
	 * @return
	 */
	public double getAmplificationScore() {
		return amplificationScore;
	}

	private long trueReach = 0;

	/**
	 * returns true reach
	 * 
	 * @return
	 */
	public long getTrueReach() {
		return trueReach;
	}

	private double delta1Day = 0;

	/**
	 * returns the 1 day delta
	 * 
	 * @return
	 */
	public double getDelta1Day() {
		return delta1Day;
	}

	private double delta5Day = 0;

	/**
	 * returns the 5 day delta
	 * 
	 * @return
	 */
	public double getDelta5Day() {
		return delta5Day;
	}

	/**
	 * returns a basic string representation of this KloutUser. Note: not
	 * suitable for serialization as it does not contain all the user data
	 * 
	 */
	public String toString() {
		return "KloutUser[twitterId=" + twitterId + ",twitterScreenName="
				+ twitterScreenName + ",kscore=" + kscore + ",slope=" + slope
				+ ",description=" + description + ",kclassId=" + kclassId
				+ ",kclass=" + kclass + ",kclassDescription="
				+ kclassDescription + ",kscoreDescription=" + kscoreDescription
				+ ",networkScore=" + networkScore + ",amplificationScore="
				+ amplificationScore + ",trueReach=" + trueReach + "]";
	}
}
