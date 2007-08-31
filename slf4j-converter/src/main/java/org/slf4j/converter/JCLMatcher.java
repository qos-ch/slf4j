package org.slf4j.converter;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * This class represents JCL to SLF4J conversion rules
 * 
 * @author jean-noelcharpin
 * 
 */
public class JCLMatcher extends AbstractMatcher {

  public JCLMatcher() {
    super();
    initRules();
  }

  protected void initRules() {
    // matching : import org.apache.commons.logging.LogFactory;
    ConversionRule cr0 = new ConversionRule(Pattern
        .compile("import\\s*+org.apache.commons.logging.LogFactory;"));
    cr0.addReplacement(Constant.INDEX_0, "import org.slf4j.LoggerFactory;");

    // matching : import org.apache.commons.logging.Log;
    ConversionRule cr1 = new ConversionRule(Pattern
        .compile("import\\s*+org.apache.commons.logging.Log;"));
    cr1.addReplacement(Constant.INDEX_0, "import org.slf4j.Logger;");

    // matching declaration and instanciation : protected Log myLog =
    // LogFactory.getFactory().getInstance(MyClass.class); //comment or other
    // instruction
    ConversionRule cr2 = new ConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr2.addReplacement(Constant.INDEX_3, "Logger");
    cr2.addReplacement(Constant.INDEX_2, "");
    cr2.addReplacement(Constant.INDEX_5, "LoggerFactory.getLogger(");

    // matching declaration and instanciation : protected static Log myLog =
    // LogFactory.getLog(MyClass.class); //comment or other instruction
    ConversionRule cr3 = new ConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr3.addReplacement(Constant.INDEX_3, "Logger");
    cr3.addReplacement(Constant.INDEX_2, "");
    cr3.addReplacement(Constant.INDEX_5, "LoggerFactory.getLogger(");

    // matching instanciation without declaration : myLog =
    // LogFactory.getFactory().getInstance(MyClass.class); //comment or other
    // instruction
    ConversionRule cr4 = new ConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr4.addReplacement(Constant.INDEX_4, "LoggerFactory.getLogger(");
    cr4.addReplacement(Constant.INDEX_2, "");

    // matching instanciation without declaration : myLog =
    // LogFactory.getLog(MyClass.class); //comment or other instruction
    ConversionRule cr5 = new ConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr5.addReplacement(Constant.INDEX_4, "LoggerFactory.getLogger(");
    cr5.addReplacement(Constant.INDEX_2, "");

    // matching declaration without instanciation : public static final Log
    // myLog //comment or other instruction
    ConversionRule cr6 = new ConversionRule(Pattern
        .compile("((\\w*+\\W*+)*)(Log)(\\s*+\\w+\\s*+;)((\\w*+\\W*+)*)"));
    cr6.addReplacement(Constant.INDEX_3, "Logger");
    cr6.addReplacement(Constant.INDEX_2, "");

    // matching incomplete instanciation : protected Log log =
    ConversionRule cr7 = new ConversionRule(Pattern
        .compile("((\\w*+\\W*+)*)(Log)(\\s+\\w+\\s*+=*\\s*+)"));
    cr7.addReplacement(Constant.INDEX_3, "Logger");
    cr7.addReplacement(Constant.INDEX_2, "");

    // matching incomlete instanciation : LogFactory.getLog(MyComponent.class);
    ConversionRule cr8 = new ConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr8.addReplacement(Constant.INDEX_3, "LoggerFactory.getLogger(");
    cr8.addReplacement(Constant.INDEX_1, "");

    // matching incomlete instanciation :
    // LogFactory.getFactory().getInstance(MyComponent.class);
    ConversionRule cr9 = new ConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr9.addReplacement(Constant.INDEX_3, "LoggerFactory.getLogger(");
    cr9.addReplacement(Constant.INDEX_1, "");

    rules = new ArrayList<ConversionRule>();
    rules.add(cr0);
    rules.add(cr1);
    rules.add(cr2);
    rules.add(cr3);
    rules.add(cr4);
    rules.add(cr5);
    rules.add(cr6);
    rules.add(cr7);
    rules.add(cr8);
    rules.add(cr9);
  }
}
