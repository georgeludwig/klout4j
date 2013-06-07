package org.klout4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.klout4j.service.Klout;
import org.klout4j.service.impl.KloutImpl;

public class KloutTest extends AbstractKloutTest {

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testApp() throws Exception {
		Klout klout=new KloutImpl(API_KEY);
        // TODO
	}
}
