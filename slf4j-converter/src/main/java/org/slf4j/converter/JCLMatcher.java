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
    SingleConversionRule cr0 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.commons.logging.LogFactory;"),
        "import org.slf4j.LoggerFactory;");

    // matching : import org.apache.commons.logging.Log;
    SingleConversionRule cr1 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.commons.logging.Log;"), 
        "import org.slf4j.Logger;");

    // matching declaration and instanciation : protected Log myLog =
    // LogFactory.getFactory().getInstance(MyClass.class); //comment or other
    // instruction
    MultiGroupConversionRule cr2 = new MultiGroupConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr2.addReplacement(3, "Logger");
    cr2.addReplacement(2, "");
    cr2.addReplacement(5, "LoggerFactory.getLogger(");

    // matching declaration and instanciation : protected static Log myLog =
    // LogFactory.getLog(MyClass.class); //comment or other instruction
    MultiGroupConversionRule cr3 = new MultiGroupConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr3.addReplacement(2, "");
    cr3.addReplacement(3, "Logger");
    cr3.addReplacement(5, "LoggerFactory.getLogger(");

    // matching instanciation without declaration : myLog =
    // LogFactory.getFactory().getInstance(MyClass.class); //comment or other
    // instruction
    MultiGroupConversionRule cr4 = new MultiGroupConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr4.addReplacement(4, "LoggerFactory.getLogger(");
    cr4.addReplacement(2, "");

    // matching instanciation without declaration : myLog =
    // LogFactory.getLog(MyClass.class); //comment or other instruction
    MultiGroupConversionRule cr5 = new MultiGroupConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr5.addReplacement(4, "LoggerFactory.getLogger(");
    cr5.addReplacement(2, "");

    // matching declaration without instanciation : public static final Log
    // myLog //comment or other instruction
    MultiGroupConversionRule cr6 = new MultiGroupConversionRule(Pattern
        .compile("((\\w*+\\W*+)*)(Log)(\\s*+\\w+\\s*+;)((\\w*+\\W*+)*)"));
    cr6.addReplacement(3, "Logger");
    cr6.addReplacement(2, "");

    // matching incomplete instanciation : protected Log log =
    MultiGroupConversionRule cr7 = new MultiGroupConversionRule(Pattern
        .compile("((\\w*+\\W*+)*)(Log)(\\s+\\w+\\s*+=*\\s*+)"));
    cr7.addReplacement(Constant.INDEX_3, "Logger");
    cr7.addReplacement(Constant.INDEX_2, "");

    // matching incomlete instanciation : LogFactory.getLog(MyComponent.class);
    MultiGroupConversionRule cr8 = new MultiGroupConversionRule(
        Pattern
            .compile("((\\w*+\\W*+)*)(LogFactory.getLog\\()(\\w+)(.class\\);)((\\w*+\\W*+)*)"));
    cr8.addReplacement(Constant.INDEX_3, "LoggerFactory.getLogger(");
    cr8.addReplacement(Constant.INDEX_1, "");

    // matching incomlete instanciation :
    // LogFactory.getFactory().getInstance(MyComponent.class);
    MultiGroupConversionRule cr9 = new MultiGroupConversionRule(
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
