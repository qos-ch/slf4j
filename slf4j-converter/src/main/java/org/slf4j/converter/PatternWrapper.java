package org.slf4j.converter;

import java.util.HashMap;
import java.util.regex.Pattern;

public class PatternWrapper  {

	private Pattern pattern;
	
	private HashMap<Integer, String> replacementMap;

	public PatternWrapper(Pattern pattern) {
		this.pattern = pattern;
		this.replacementMap = new HashMap<Integer, String>();
	}


	public Pattern getPattern() {
		return pattern;
	}

	public void addReplacement(Integer groupIndex, String replacement) {
		replacementMap.put(groupIndex, replacement);
	}	
	
	public String getReplacement(Integer groupIndex) {
		return replacementMap.get(groupIndex);
	}
	
}
