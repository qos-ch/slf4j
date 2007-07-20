package org.slf4j.converter;

import java.util.TreeMap;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

public class JCLMatcher extends AbstractMatcher {

	public JCLMatcher() {
		super();
		logger = LoggerFactory.getLogger(JCLMatcher.class);
		initRules();
	}

	protected void initRules() {
		PatternWrapper p0 = new PatternWrapper("0", Pattern.compile("import\\s*+org.apache.commons.logging.LogFactory;"));
		PatternWrapper p1 = new PatternWrapper("1", Pattern.compile("import\\s*+org.apache.commons.logging.Log;"));
		PatternWrapper p2 = new PatternWrapper("2",Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		PatternWrapper p3 = new PatternWrapper("3",Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		PatternWrapper p4 = new PatternWrapper("4",Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		PatternWrapper p5 = new PatternWrapper("5",Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		PatternWrapper p6 = new PatternWrapper("6",Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(Log)(\\s*+\\w+\\s*+;)((\\w*+\\W*+\\.*)*;*+)"));

		ReplacementWrapper r0 = new ReplacementWrapper("0","import org.slf4j.LoggerFactory;");
		ReplacementWrapper r1 = new ReplacementWrapper("0","import org.slf4j.Logger;");
		ReplacementWrapper r2 = new ReplacementWrapper("3", "Logger");
		r2.addReplacement("2", "");
		r2.addReplacement("5", "LoggerFactory.getLogger(");
		ReplacementWrapper r3 = new ReplacementWrapper("4","LoggerFactory.getLogger(");
		r3.addReplacement("2", "");
		ReplacementWrapper r4 = new ReplacementWrapper("3", "Logger");
		r4.addReplacement("2", "");
		
		rulesMap = new TreeMap<PatternWrapper, ReplacementWrapper>();
		rulesMap.put(p0, r0);
		rulesMap.put(p1, r1);
		rulesMap.put(p2, r2);
		rulesMap.put(p3, r2);
		rulesMap.put(p4, r3);
		rulesMap.put(p5, r3);
		rulesMap.put(p6, r4);
	}
}
