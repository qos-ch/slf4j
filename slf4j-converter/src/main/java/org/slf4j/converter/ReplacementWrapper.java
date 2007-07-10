package org.slf4j.converter;

import java.util.HashMap;

public class ReplacementWrapper {

	private HashMap<String, String> replacementMap;

	public ReplacementWrapper(String groupIndex, String replacement) {
		replacementMap = new HashMap<String, String>();
		replacementMap.put(groupIndex, replacement);
	}

	public void addReplacement(String groupIndex, String replacement) {
		replacementMap.put(groupIndex, replacement);
	}

	public String getReplacement(int groupIndex) {
		String resultIndex = Integer.toString(groupIndex);
		return replacementMap.get(resultIndex);
	}

}
