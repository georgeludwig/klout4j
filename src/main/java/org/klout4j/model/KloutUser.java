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
public class KloutUser extends KloutScore implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long kloutId;
    private String screenName;

    /**
     * Constructor that takes a map of properties.
     *
     * @param attributes
     */
    @SuppressWarnings("unchecked")
    public KloutUser(Map<String, Object> attributes) {
        super(attributes);
        kloutId = Long.parseLong(attributes.get("kloutId").toString());
        screenName = (String) (attributes.get("nick"));
        Map<String, Object> scoreDeltas = (Map<String, Object>) attributes.get("scoreDeltas");
        if (scoreDeltas != null && !scoreDeltas.isEmpty()) {
            scoreDayChange = scoreDeltas.get("dayChange") != null ?
                    Double.parseDouble(scoreDeltas.get("dayChange").toString()) : 0;
            scoreWeekChange = scoreDeltas.get("weekChange") != null ?
                    Double.parseDouble(scoreDeltas.get("weekChange").toString()) : 0;
            scoreMonthChange = scoreDeltas.get("monthChange") != null ?
                    Double.parseDouble(scoreDeltas.get("monthChange").toString()) : 0;
        }
    }

    public Long getKloutId() {
        return kloutId;
    }

    public String getScreenName() {
        return screenName;
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
                '}';
    }
}
