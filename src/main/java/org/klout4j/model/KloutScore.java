package org.klout4j.model;

import org.json.simple.JSONObject;

import java.util.Map;

/**
 * A class representing the Klout score of a Twitter user.
 * <p/>
 * For more info see http://developer.klout.com/page/Klout_API_Onesheet
 *
 * @author George
 * @see KloutUser
 */
public class KloutScore implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    protected double score = 0;
    protected double scoreDayChange;
    protected double scoreWeekChange;
    protected double scoreMonthChange;
    protected String bucket;

    @SuppressWarnings("unchecked")
    public KloutScore(Map<String, Object> attributes) {
        if (attributes.containsKey("score")) {
            Object scoreObject = attributes.get("score");
            if (scoreObject instanceof JSONObject) {
                JSONObject o = (JSONObject) scoreObject;
                if (o.containsKey("score")) {
                    score = Double.parseDouble(o.get("score").toString());
                }
                if (o.containsKey("bucket")) {
                    bucket = (String) o.get("bucket");
                }
            } else {
                score = Double.parseDouble(attributes.get("score").toString());
            }
        }
        if (attributes.containsKey("bucket")) {
            bucket = (String) attributes.get("bucket");
        }
        Map<String, Object> scoreDeltas = (Map<String, Object>) attributes.get("scoreDelta");
        if (scoreDeltas != null && !scoreDeltas.isEmpty()) {
            scoreDayChange = scoreDeltas.get("dayChange") != null ?
                    Double.parseDouble(scoreDeltas.get("dayChange").toString()) : 0;
            scoreWeekChange = scoreDeltas.get("weekChange") != null ?
                    Double.parseDouble(scoreDeltas.get("weekChange").toString()) : 0;
            scoreMonthChange = scoreDeltas.get("monthChange") != null ?
                    Double.parseDouble(scoreDeltas.get("monthChange").toString()) : 0;
        }
    }


    /**
     * returns the Klout score as a double
     *
     * @return
     */
    public double getScore() {
        return score;
    }

    public double getScoreDayChange() {
        return scoreDayChange;
    }

    public double getScoreWeekChange() {
        return scoreWeekChange;
    }

    public double getScoreMonthChange() {
        return scoreMonthChange;
    }

    public String getBucket() {
        return bucket;
    }

    /**
     * returns a String representation of this score
     */
    @Override
    public String toString() {
        return "KloutScore{" +
                "score=" + score +
                ", scoreDayChange=" + scoreDayChange +
                ", scoreWeekChange=" + scoreWeekChange +
                ", scoreMonthChange=" + scoreMonthChange +
                '}';
    }
}
