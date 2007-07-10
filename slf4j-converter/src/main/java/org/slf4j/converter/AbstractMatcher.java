package org.slf4j.converter;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

public abstract class AbstractMatcher {

	protected Logger logger;

	protected TreeMap<PatternWrapper, ReplacementWrapper> rulesMap;

	protected Writer writer;

	public AbstractMatcher() {
	}

	public static AbstractMatcher getMatcherImpl() {
		// TODO criterias
		return new JCLMatcher();
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public void matches(String text) {
		PatternWrapper patternWrapper;
		Pattern pattern;
		Matcher matcher;
		String replacementText;
		ReplacementWrapper replacementWrapper;
		Iterator rulesIter = rulesMap.keySet().iterator();
		boolean found = false;
		while (rulesIter.hasNext()) {
			patternWrapper = (PatternWrapper) rulesIter.next();
			pattern = patternWrapper.getPattern();
			matcher = pattern.matcher(text);
			if (matcher.matches()) {
				logger.info("match " + text);
				replacementWrapper = rulesMap.get(patternWrapper);
				StringBuffer replacementBuffer = new StringBuffer();
				for (int group = 0; group <= matcher.groupCount(); group++) {
					replacementText = replacementWrapper.getReplacement(group);
					if (replacementText != null) {
						logger.info("replacing group " + group + " : "
								+ matcher.group(group) + " with "
								+ replacementText);
						replacementBuffer.append(replacementText);
					} else if (group > 0) {
						logger.info("conserving group " + group + " : "
								+ matcher.group(group));
						replacementBuffer.append(matcher.group(group));
					}
				}
				writer.rewrite(matcher, replacementBuffer.toString());
				found = true;
				break;
			}
			// else if (matcher.find()) {
			// logger.info("found " + text + " pattern " + pattern.toString());
			// replacement = (String) rulesMap.get(patternWrapper);
			// writer.rewrite(matcher, replacement);
			// found = true;
			// break;
			// }
		}
		if (!found) {
			writer.write(text);
		}
	}

	protected abstract void initRules();
}
