package org.klout4j.model;

import java.util.Map;

/**
 * A class representing a Klout user. Contains details on the user's Klout score.
 * Response example:
 * {
 * "kloutId": "635263",
 * "nick": "jtimberlake",
 * "score": {
 * "score": 92.21004781265579,
 * "bucket": "90-100"
 * },
 * "scoreDeltas": {
 * "dayChange": -0.071824172738431,
 * "weekChange": -0.20777379569214816,
 * "monthChange": -0.3548751682149742
 * }
 * }
 *
 * @author George
 */
public class KloutUser implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long kloutId;
    private String screenName;
    private Double score;
    private String bucket;
    private Double scoreDayChange;
    private Double scoreWeekChange;
    private Double scoreMonthChange;


    /**
     * Constructor that takes a map of properties.
     *
     * @param attributes
     */
    @SuppressWarnings("unchecked")
    public KloutUser(Map<String, Object> attributes) {
        kloutId = Long.parseLong(attributes.get("kloutId").toString());
        screenName = (String) (attributes.get("nick"));
        Map<String, Object> scoreAttributes = (Map<String, Object>) attributes.get("score");
        score = Double.parseDouble(scoreAttributes.get("score").toString());
        bucket = (String) scoreAttributes.get("bucket");
        Map<String, Object> scoreDeltas = (Map<String, Object>) attributes.get("scoreDeltas");
        scoreDayChange = Double.parseDouble(scoreDeltas.get("dayChange").toString());
        scoreWeekChange = Double.parseDouble(scoreDeltas.get("weekChange").toString());
        scoreMonthChange = Double.parseDouble(scoreDeltas.get("monthChange").toString());
    }

    public Long getKloutId() {
        return kloutId;
    }

    public String getScreenName() {
        return screenName;
    }

    public Double getScore() {
        return score;
    }

    public String getBucket() {
        return bucket;
    }

    public Double getScoreDayChange() {
        return scoreDayChange;
    }

    public Double getScoreWeekChange() {
        return scoreWeekChange;
    }

    public Double getScoreMonthChange() {
        return scoreMonthChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KloutUser kloutUser = (KloutUser) o;

        return !(kloutId != null ? !kloutId.equals(kloutUser.kloutId) : kloutUser.kloutId != null);

    }

    @Override
    public int hashCode() {
        return kloutId != null ? kloutId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "KloutUser{" +
                "kloutId=" + kloutId +
                ", screenName='" + screenName + '\'' +
                ", score=" + score +
                ", bucket='" + bucket + '\'' +
                '}';
    }
}
