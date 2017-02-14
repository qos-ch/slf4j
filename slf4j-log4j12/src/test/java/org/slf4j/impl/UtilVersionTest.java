package org.slf4j.impl;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class UtilVersionTest {

	@Test
	public void test() {
		System.out.println(System.getProperty("java.version"));
		assertEquals(6, VersionUtil.getJavaMajorVersion("1.6"));
		assertEquals(7, VersionUtil.getJavaMajorVersion("1.7.0_21-b11"));
		assertEquals(8, VersionUtil.getJavaMajorVersion("1.8.0_25"));
	}
	
	@Ignore
	@Test  // requires Java 9 to pass
	public void testJava9() {
		assertEquals(9, VersionUtil.getJavaMajorVersion("9ea"));
	}

}
