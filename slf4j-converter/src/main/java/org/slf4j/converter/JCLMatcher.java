package org.slf4j.converter;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class JCLMatcher extends AbstractMatcher {

	public JCLMatcher() {
		super();
		initRules();
	}

	protected void initRules() {
		PatternWrapper p0 = new PatternWrapper(Pattern.compile("import\\s*+org.apache.commons.logging.LogFactory;"));
		PatternWrapper p1 = new PatternWrapper(Pattern.compile("import\\s*+org.apache.commons.logging.Log;"));
		PatternWrapper p2 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		PatternWrapper p3 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		PatternWrapper p4 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		PatternWrapper p5 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		PatternWrapper p6 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(Log)(\\s*+\\w+\\s*+;)((\\w*+\\W*+\\.*)*;*+)"));

		p0.addReplacement(Constant.INDEX_0,"import org.slf4j.LoggerFactory;");
		p1.addReplacement(Constant.INDEX_0,"import org.slf4j.Logger;");
		p2.addReplacement(Constant.INDEX_3,"Logger");
		p2.addReplacement(Constant.INDEX_2,"");
		p2.addReplacement(Constant.INDEX_5,"LoggerFactory.getLogger(");
		p3.addReplacement(Constant.INDEX_3,"Logger");
		p3.addReplacement(Constant.INDEX_2,"");
		p3.addReplacement(Constant.INDEX_5,"LoggerFactory.getLogger(");
		p4.addReplacement(Constant.INDEX_4,"LoggerFactory.getLogger(");
		p4.addReplacement(Constant.INDEX_2,"");
		p5.addReplacement(Constant.INDEX_4,"LoggerFactory.getLogger(");
		p5.addReplacement(Constant.INDEX_2,"");
		p6.addReplacement(Constant.INDEX_3,"Logger");
		p6.addReplacement(Constant.INDEX_2,"");
		
		rules = new ArrayList<PatternWrapper>();
		rules.add(p0);
		rules.add(p1);
		rules.add(p2);
		rules.add(p3);
		rules.add(p4);
		rules.add(p5);
		rules.add(p6);
	}
}
