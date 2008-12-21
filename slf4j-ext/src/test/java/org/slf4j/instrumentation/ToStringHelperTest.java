package org.slf4j.instrumentation;

import junit.framework.TestCase;

public class ToStringHelperTest extends TestCase {

	public void testRenderer() {
		assertEquals("", "null", ToStringHelper.render(null));
		assertEquals("", "a", ToStringHelper.render("a"));
		assertEquals("", "[]", ToStringHelper.render(new String[0]));

		assertEquals("", "[a]", ToStringHelper.render(new String[] { "a" }));

		assertEquals("", "[a, b]", ToStringHelper.render(new String[] { "a",
				"b" }));

		assertEquals("", "[a, b, c]", ToStringHelper.render(new String[] { "a",
				"b", "c" }));

		assertEquals("", "[[a], [b, c]]", ToStringHelper.render(new String[][] {
				{ "a" }, { "b", "c" } }));

		assertEquals("", "[0, [a], [b, c]]", ToStringHelper
				.render(new Object[] { "0", new String[] { "a" },
						new Object[] { "b", "c" } }));

		assertEquals("", "[1]", ToStringHelper.render(new int[] { 1 }));

		assertEquals("", "[1, 2, 3]", ToStringHelper
				.render(new int[] { 1, 2, 3 }));

		assertEquals("", "[1, 2, 3]", ToStringHelper.render(new long[] { 1, 2,
				3 }));

		assertEquals("", "[1, 2, 3]", ToStringHelper.render(new short[] { 1, 2,
				3 }));

		assertEquals("", "[[1, 2], [], [3, 4]]", ToStringHelper
				.render(new byte[][] { { 1, 2 }, {}, { 3, 4 } }));

		assertEquals("", "[1.0, 2.0, 3.0]", ToStringHelper.render(new float[] {
				1, 2, 3 }));

		assertEquals("", "[1.0, 2.0, 3.0]", ToStringHelper.render(new double[] {
				1, 2, 3 }));

		assertEquals("", "[[1.0, 2.0, 3.0]]", ToStringHelper
				.render(new double[][] { { 1, 2, 3 } }));

		assertEquals("", "[true, false, true]", ToStringHelper
				.render(new boolean[] { true, false, true }));
	}

}
