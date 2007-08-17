package org.slf4j.converter;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class JCLMatcher extends AbstractMatcher {

	public JCLMatcher() {
		super();
		initRules();
	}

	protected void initRules() {
		//matching : import org.apache.commons.logging.LogFactory; 
		PatternWrapper p0 = new PatternWrapper(Pattern.compile("import\\s*+org.apache.commons.logging.LogFactory;"));
		//matching : import org.apache.commons.logging.Log; 
		PatternWrapper p1 = new PatternWrapper(Pattern.compile("import\\s*+org.apache.commons.logging.Log;"));
		//matching declaration and instanciation : protected Log myLog = LogFactory.getFactory().getInstance(MyClass.class); //comment or other instruction 
		PatternWrapper p2 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		//matching declaration and instanciation : protected static Log myLog = LogFactory.getLog(MyClass.class); //comment or other instruction 
		PatternWrapper p3 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		//matching instanciation without declaration :  myLog = LogFactory.getFactory().getInstance(MyClass.class); //comment or other instruction 
		PatternWrapper p4 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		//matching instanciation without declaration :  myLog = LogFactory.getLog(MyClass.class); //comment or other instruction 
		PatternWrapper p5 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		//matching declaration without instanciation : public static final Log myLog //comment or other instruction
		PatternWrapper p6 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(Log)(\\s*+\\w+\\s*+;)((\\w*+\\W*+\\.*)*;*+)"));
		//matching incomplete instanciation : protected Log log =
		PatternWrapper p7 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(Log)(\\s+\\w+\\s*+=*\\s*+)"));
		//matching incomlete instanciation : LogFactory.getLog(MyComponent.class);
		PatternWrapper p8 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		//matching incomlete instanciation : LogFactory.getFactory().getInstance(MyComponent.class);
		PatternWrapper p9 = new PatternWrapper(Pattern.compile("((\\w*+\\W*+\\.*)*;*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+\\.*)*;*+)"));
		
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
		p7.addReplacement(Constant.INDEX_3, "Logger");
		p7.addReplacement(Constant.INDEX_2, "");
		p8.addReplacement(Constant.INDEX_3, "LoggerFactory.getLogger(");
		p8.addReplacement(Constant.INDEX_1, "");
		p9.addReplacement(Constant.INDEX_3, "LoggerFactory.getLogger(");
		p9.addReplacement(Constant.INDEX_1, "");
		
		
		rules = new ArrayList<PatternWrapper>();
		rules.add(p0);
		rules.add(p1);
		rules.add(p2);
		rules.add(p3);
		rules.add(p4);
		rules.add(p5);
		rules.add(p6);
		rules.add(p7);
		rules.add(p8);
		rules.add(p9);
	}
}
