package org.klout4j.service.impl;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.klout4j.AbstractKloutTest;
import org.klout4j.model.IdentityType;
import org.klout4j.model.KloutUser;
import org.klout4j.model.RequestType;

/**
 * @author Marina Chernyavskaya
 * @date 6/10/13
 */
public class TestKloutImpl extends AbstractKloutTest {

    private KloutImpl klout = new KloutImpl(API_KEY);
    Long TEST_ID = 555l;
    Long KLOUT_ID = 635263l; // jtimberlake

    @Before
    public void setUp() throws Exception {
        klout.setApiKey(API_KEY);
    }

    @Test
    public void testGetIdentityRequestString() throws Exception {
        String result = klout.getIdentityRequestString(IdentityType.twitterId, TEST_ID);
        String expected = klout.getRootUrl() + "identity.json/tw/" + TEST_ID + "?key=" + API_KEY;
        Assert.assertEquals(expected, result);

    }

    @Test
    public void testGetIdentityRequestString_GP() throws Exception {
        String result = klout.getIdentityRequestString(IdentityType.googlePlus, TEST_ID);
        String expected = klout.getRootUrl() + "identity.json/gp/" + TEST_ID + "?key=" + API_KEY;
        Assert.assertEquals(expected, result);

    }

    @Test
    public void testGetIdentityRequestString_IG() throws Exception {
        String result = klout.getIdentityRequestString(IdentityType.instagram, TEST_ID);
        String expected = klout.getRootUrl() + "identity.json/ig/" + TEST_ID + "?key=" + API_KEY;
        Assert.assertEquals(expected, result);

    }

    @Test
    public void testGetIdentityRequestString_Klout() throws Exception {
        String result = klout.getIdentityRequestString(IdentityType.kloutId, TEST_ID);
        String expected = klout.getRootUrl() + "identity.json/klout/" + TEST_ID + "/tw?key=" + API_KEY;
        Assert.assertEquals(expected, result);

    }

    @Test
    public void testGetIdentityRequestString_ScreenName() throws Exception {
        String name = "aplusk";
        String result = klout.getIdentityRequestString(IdentityType.twitterScreenName, name);
        String expected = klout.getRootUrl() + "identity.json/twitter?screenName=" + name + "&key=" + API_KEY;
        Assert.assertEquals(expected, result);

    }

    @Test
    public void testGetIdentityRequestString_ShowUser() throws Exception {
        String result = klout.getRequestString(RequestType.SHOW_USER, KLOUT_ID);
        String expected = klout.getRootUrl() + "user.json/" + KLOUT_ID + "?key=" + API_KEY;
        Assert.assertEquals(expected, result);

    }

    @Test
    public void testGetUser() throws Exception {
        KloutUser user = klout.showUser(KLOUT_ID);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getKloutId());
        Assert.assertEquals(user.getKloutId(), KLOUT_ID);
        Assert.assertNotNull(user.getBucket());
        Assert.assertNotNull(user.getScreenName());
        Assert.assertTrue(user.getScore() > 0);
        Assert.assertNotNull(user.getBucket());
    }
}
