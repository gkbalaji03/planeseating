package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestSeatingTest {
	
	TestSeating test = new TestSeating();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		test.getInt(null);
	}

}
