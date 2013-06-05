package org.klout4j;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KloutTest extends AbstractKloutTest {

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testApp() throws Exception {
		Klout klout=new Klout(API_KEY);
		List<String>userList=new ArrayList<String>();
		userList.add("Scobelizer");
		Map<String,KloutUser>kloutUserMap=klout.showUsers(userList);
		assertTrue(kloutUserMap.size()==userList.size());
		Map<String,Topics>kloutTopicsMap=klout.topics(userList);
		assertTrue(kloutTopicsMap.size()==userList.size());
		Map<String,KloutScore>kloutScoresMap=klout.kloutScore(userList);
		assertTrue(kloutScoresMap.size()==userList.size());
		
	}
}
