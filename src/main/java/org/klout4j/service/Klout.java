package org.klout4j.service;

import org.klout4j.exception.KloutException;
import org.klout4j.model.KloutScore;
import org.klout4j.model.KloutUser;
import org.klout4j.model.Topics;

import java.util.List;
import java.util.Map;

/**
 * @author Marina Chernyavskaya
 * @date 6/7/13
 */
public interface Klout {

    Long getKloutId(Long twitterId);

    Long getKloutId(String twitterScreenName);

    KloutScore kloutScore(Long kloutId) throws KloutException;

    Map<String, KloutScore> kloutScore(List<Long> kloutIds) throws KloutException;

    KloutUser showUser(Long kloutId) throws KloutException;

    Map<String, KloutUser> showUsers(List<Long> kloutIds) throws KloutException;

    Topics topics(Long kloutId) throws KloutException;

    Map<String, Topics> topics(List<Long> kloutIds) throws KloutException;
}
