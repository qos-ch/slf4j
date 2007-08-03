package org.slf4j.converter;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class AbstractMatcher {

	protected TreeMap<PatternWrapper, ReplacementWrapper> rulesMap;

	protected boolean commentConversion = true;

	protected boolean blockComment = false;


	public AbstractMatcher() {
	}

	public static AbstractMatcher getMatcherImpl(int conversionType) {
		if(conversionType==Constant.JCL_TO_SLF4J){
			return new JCLMatcher();
		}
		return null;
	}

	public void setCommentConversion(boolean commentConversion) {
		this.commentConversion = commentConversion;
	}

	/**
	 * 
	 * @param text
	 */
	public String replace(String text) {
		if (isTextConvertible(text)) {
			PatternWrapper patternWrapper;
			Pattern pattern;
			Matcher matcher;
			String replacementText;
			ReplacementWrapper replacementWrapper;
			Iterator rulesIter = rulesMap.keySet().iterator();
			while (rulesIter.hasNext()) {
				patternWrapper = (PatternWrapper) rulesIter.next();
				pattern = patternWrapper.getPattern();
				matcher = pattern.matcher(text);
				if (matcher.matches()) {
					System.out.println("matching " + text);
					replacementWrapper = rulesMap.get(patternWrapper);
					StringBuffer replacementBuffer = new StringBuffer();
					for (int group = 0; group <= matcher.groupCount(); group++) {
						replacementText = replacementWrapper.getReplacement(group);
						if (replacementText != null) {
							System.out.println("replacing group " + group + " : "
								+ matcher.group(group) + " with "
								+ replacementText);
							replacementBuffer.append(replacementText);
						} 
						else if (group > 0) {
						replacementBuffer.append(matcher.group(group));
						}
					}
					return replacementBuffer.toString();					
				}
			}
		}
		return text;
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
		} else if (blockComment || text.startsWith(Constant.LINE_COMMENT)) {
			isConvertible = false;
		} else if (text.startsWith(Constant.BLOCK_COMMENT_START)) {
			blockComment = true;
			isConvertible = false;
		}
		if (text.endsWith(Constant.BLOCK_COMMENT_END)) {
			blockComment = false;
		}
		return isConvertible;
	}

	protected abstract void initRules();
}
