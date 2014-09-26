package org.slf4j.profiler;

import org.slf4j.Logger;

public interface Optional
{
	Profiler get();

	Optional orFind(Object o);

	Optional orFind(Class clazz);

	Optional orFind(String name);

	Profiler.Builder orBuild(Logger logger);

	Profiler.Builder orBuild(String name, Logger logger);

	Profiler orNull();
}
