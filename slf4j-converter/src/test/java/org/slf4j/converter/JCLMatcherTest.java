package org.slf4j.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

public class JCLMatcherTest extends TestCase {

	
	
	public void testConversion() throws IOException {
		AbstractMatcher jclMatcher = AbstractMatcher.getMatcherImpl();
		Writer writer = new Writer();
		File jclFile = new File("jclConversionTest");
		writer.initFileWriter(jclFile);
		jclMatcher.setWriter(writer);
		jclMatcher.setCommentConversion(true);

		jclMatcher.matches("import org.apache.commons.logging.LogFactory;");
		jclMatcher.matches("import org.apache.commons.logging.Log;");
		jclMatcher.matches("Log l = LogFactory.getLog(MyClass.class);");
		jclMatcher.matches("public Log mylog=LogFactory.getLog(MyClass.class);");
		jclMatcher.matches("public static Log mylog1 = LogFactory.getLog(MyClass.class);");
		jclMatcher.matches("Log log3=LogFactory.getFactory().getInstance(MyClass.class);");
		jclMatcher.matches("Log mylog4 = LogFactory.getFactory().getInstance(MyClass.class);//logger instanciation");
		jclMatcher.matches("Log myLog6;//logger declaration");
		jclMatcher.matches("//log7=LogFactory.getFactory().getInstance(MyClass.class);");
		jclMatcher.matches("log8 =LogFactory.getFactory().getInstance(MyClass.class);");
		jclMatcher.matches("myLog9 = LogFactory.getLog(MyClass.class);");
		jclMatcher.matches("private Log mylog10;");
		jclMatcher.matches("protected final Log myLog11;");
		jclMatcher.matches("public static final Log myLog12;");
		jclMatcher.matches("System.out.println(\"Log\") ;System.out.println(\"Log2\");  public static final Log myLog13;");
		jclMatcher.matches("public static final Log myLog14;System.out.println(\"Log\");");
		jclMatcher.matches("System.out.println(\"\");public static final Log myLog15;System.out.println(\"Log\")  ;System.out.println(\"Log2\");");
		jclMatcher.matches("((Pojo)pojo.getPojo()).get(\"pojo\",pojo);public static final Log myLog16;");
		
		jclMatcher.getWriter().closeFileWriter();
		BufferedReader breader = new BufferedReader(new FileReader(jclFile));
	
		
		assertEquals(breader.readLine(),"import org.slf4j.LoggerFactory;");
		assertEquals(breader.readLine(),"import org.slf4j.Logger;");
		assertEquals(breader.readLine(),"Logger l = LoggerFactory.getLogger(MyClass.class);");
		assertEquals(breader.readLine(),"public Logger mylog=LoggerFactory.getLogger(MyClass.class);");
		assertEquals(breader.readLine(),"public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);");
		assertEquals(breader.readLine(),"Logger log3=LoggerFactory.getLogger(MyClass.class);");
		assertEquals(breader.readLine(),"Logger mylog4 = LoggerFactory.getLogger(MyClass.class);//logger instanciation");
		assertEquals(breader.readLine(),"Logger myLog6;//logger declaration");
		assertEquals(breader.readLine(),"//log7=LoggerFactory.getLogger(MyClass.class);");
		assertEquals(breader.readLine(),"log8 =LoggerFactory.getLogger(MyClass.class);");
		assertEquals(breader.readLine(),"myLog9 = LoggerFactory.getLogger(MyClass.class);");
		assertEquals(breader.readLine(),"private Logger mylog10;");
		assertEquals(breader.readLine(),"protected final Logger myLog11;");
		assertEquals(breader.readLine(),"public static final Logger myLog12;");
		assertEquals(breader.readLine(),"System.out.println(\"Log\") ;System.out.println(\"Log2\");  public static final Logger myLog13;");
		assertEquals(breader.readLine(),"public static final Logger myLog14;System.out.println(\"Log\");");
		assertEquals(breader.readLine(),"System.out.println(\"\");public static final Logger myLog15;System.out.println(\"Log\")  ;System.out.println(\"Log2\");");
		assertEquals(breader.readLine(),"((Pojo)pojo.getPojo()).get(\"pojo\",pojo);public static final Logger myLog16;");
	}
	

	



}
