package org.slf4j.profiler.logging;

import org.slf4j.profiler.Profiler;

public interface LoggerOutput
{
	String format(Profiler profiler);
}
