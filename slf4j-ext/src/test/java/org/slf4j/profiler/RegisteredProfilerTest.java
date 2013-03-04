package org.slf4j.profiler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RegisteredProfilerTest {

	private static final Logger LOGGER = LoggerFactory
		.getLogger(RegisteredProfilerTest.class);

	@Test
	public void testBuildProfiler() throws Exception
	{
		Profiler first = RegisteredProfiler.build("first", LOGGER).build();

		assertEquals("first", first.getName());
		assertSame(LOGGER, first.getLogger());
    assertSame(ProfilerRegistry.getThreadContextInstance(),
        first.getProfilerRegistry());
  }

  @Test
  public void testFindPreRegisteredProfiler() throws Exception {
    Profiler first = RegisteredProfiler.build("first", LOGGER).build();

    Profiler result = RegisteredProfiler.findProfiler("first").get();

    assertSame(
        "Expected the found profiler to be the same as the first one registered",
        first, result);
  }

  @Test
  public void testCantFindPreRegisteredProfiler() throws Exception {
    try {
      RegisteredProfiler.findProfiler("first").get();
      fail("This should not have found a profiler");
    } catch (NullPointerException e) {
      assertEquals("No Profiler found", e.getMessage());
    }
  }

  @Test
  public void testBuildProfilerWhenPreRegisteredProfilerNotFound()
      throws Exception {
    Profiler result = RegisteredProfiler.findProfiler("second").orBuild(LOGGER)
        .build();

    assertEquals("second", result.getName());
  }

  @Test
  public void testBuildProfilerWhenPreRegisteredProfilerIsFound()
      throws Exception {
    Profiler first = RegisteredProfiler.build("first", LOGGER).build();

    Profiler result = RegisteredProfiler.findProfiler("first").orBuild(LOGGER)
        .build();

    assertSame(first, result);
  }

  @Test
  public void testFindThePreRegisteredProfilerWhenTheFirstCantBeFound()
      throws Exception {
    Profiler first = RegisteredProfiler.build("first", LOGGER).build();

    Profiler result = RegisteredProfiler.findProfiler("second").orFind("first")
        .get();

    assertSame(first, result);
  }

  @Test
  public void testNDepthOptionalChaining() throws Exception {
    Profiler result = RegisteredProfiler.findProfiler("first").orFind(this)
        .orFind("second").orBuild("third", LOGGER).build();

    assertEquals("third", result.getName());
  }

  @Test
  public void testReturnFoundIfPreRegistered() throws Exception {
    Profiler first = RegisteredProfiler.build("first", LOGGER).build();

    Profiler result = RegisteredProfiler.findProfiler("first").orNull();

    assertNotNull(result);
    assertSame(first, result);
  }

  @Test
  public void testReturnNullIfNotPreRegistered() throws Exception {
    Profiler result = RegisteredProfiler.findProfiler("first").orNull();

    assertNull(result);
  }

  @Before
  public void resetRegisteredProfilers() {
    ProfilerRegistry.getThreadContextInstance().clear();
  }
}
