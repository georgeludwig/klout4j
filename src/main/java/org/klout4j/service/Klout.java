package org.klout4j.service;

import org.klout4j.exception.KloutException;
import org.klout4j.model.KloutScore;
import org.klout4j.model.KloutUser;
import org.klout4j.model.Topics;

/**
 * @author Marina Chernyavskaya
 * @date 6/7/13
 */
public interface Klout {

    Long getKloutId(Long twitterId);

    Long getKloutId(String twitterScreenName);

    KloutScore kloutScore(Long kloutId) throws KloutException;

    KloutUser showUser(Long kloutId) throws KloutException;

    Topics topics(Long kloutId) throws KloutException;
}