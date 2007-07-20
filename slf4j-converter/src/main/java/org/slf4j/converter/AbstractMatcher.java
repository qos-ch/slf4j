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

	protected boolean commentConversion = true;

	protected boolean blockComment = false;

	private final static String LINE_COMMENT = "//";

	private final static String BLOCK_COMMENT_START = "/*";

	private final static String BLOCK_COMMENT_END = "*/";

	public AbstractMatcher() {
	}

	public static AbstractMatcher getMatcherImpl() {
		// TODO criterias to select concrete matcher impl. and comentConversion mode
		return new JCLMatcher();
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}
	
	public Writer getWriter(){
		return writer;
	}
	
	public void setCommentConversion(boolean commentConversion){
		this.commentConversion = commentConversion;
	}

	/**
	 * 
	 * @param text
	 */
	public void matches(String text) {
		if (isTextConvertible(text)) {
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
					logger.info("matching " + text);
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
			}
			if (!found) {
				writer.write(text);
			}
		} else {
			writer.write(text);
		}
	}

	
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	private boolean isTextConvertible(String text) {
		boolean isConvertible = true;
		if (text.trim().length() == 0) {
			isConvertible = false;
		} else if (commentConversion) {
			isConvertible = true;
		} else if (blockComment || text.startsWith(LINE_COMMENT)) {
			isConvertible = false;
		}
		else if (text.startsWith(BLOCK_COMMENT_START)) {
			blockComment = true;
			isConvertible = false;
		}
		if (text.endsWith(BLOCK_COMMENT_END)) {
			blockComment = false;
		}
		return isConvertible;
	}

	protected abstract void initRules();
}
