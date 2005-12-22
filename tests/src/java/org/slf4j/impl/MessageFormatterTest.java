/* 
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.CH
 * 
 * All rights reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 * 
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */

package org.slf4j.impl;

import org.slf4j.impl.MessageFormatter;

import junit.framework.TestCase;


/**
 * @author Ceki Gulcu
 *
 */
public class MessageFormatterTest extends TestCase {
  
  public void testNull() {
    String result;
    Integer i3 = new Integer(3);
    
    result = MessageFormatter.format(null, i3);
    assertEquals(null, result);
  }
  
  public void test1Param() {
    String result;
    Integer i3 = new Integer(3);
    
    result = MessageFormatter.format("Value is {}.", i3);
    assertEquals("Value is 3.", result);

    result = MessageFormatter.format("Value is {", i3);
    assertEquals("Value is {", result);
    
    result = MessageFormatter.format("Value is {}.", null);
    assertEquals("Value is null.", result);

    result = MessageFormatter.format("{} is larger than 2.", i3);
    assertEquals("3 is larger than 2.", result);

    result = MessageFormatter.format("No subst", i3);
    assertEquals("No subst", result);
    
    result = MessageFormatter.format("Incorrect {subst", i3);
    assertEquals("Incorrect {subst", result);
    
    result = MessageFormatter.format("Escaped \\{} subst", i3);
    assertEquals("Escaped {} subst", result);

    result = MessageFormatter.format("\\{Escaped", i3);
    assertEquals("{Escaped", result);

    result = MessageFormatter.format("\\{}Escaped", i3);
    assertEquals("{}Escaped", result);
    
    result = MessageFormatter.format("File name is \\{{}}.", "App folder.zip");
    assertEquals("File name is {App folder.zip}.", result);
  }
  
  public void test2Param() {
    String result;
    Integer i1 = new Integer(1);
    Integer i2 = new Integer(2);
    
    result = MessageFormatter.format("Value {} is smaller than {}.", i1, i2);
    assertEquals("Value 1 is smaller than 2.", result);
    
    result = MessageFormatter.format("Value {} is smaller than {}", i1, i2);
    assertEquals("Value 1 is smaller than 2", result);
    
    result = MessageFormatter.format("{}{}", i1, i2);
    assertEquals("12", result);
    
    result = MessageFormatter.format("Val1={}, Val2={", i1, i2);
    assertEquals("Val1=1, Val2={", result);

    result = MessageFormatter.format("Value {} is smaller than \\{}", i1, i2);
    assertEquals("Value 1 is smaller than {}", result);
    
    result = MessageFormatter.format("Value {} is smaller than \\{} tail", i1, i2);
    assertEquals("Value 1 is smaller than {} tail", result);    

    result = MessageFormatter.format("Value {} is smaller than \\{", i1, i2);
    assertEquals("Value 1 is smaller than \\{", result);  
    
    result = MessageFormatter.format("Value {} is smaller than \\{tail", i1, i2);
    assertEquals("Value 1 is smaller than {tail", result);  
  
    
    result = MessageFormatter.format("Value \\{} is smaller than {}", i1, i2);
    assertEquals("Value {} is smaller than 1", result);    
  }
  
  public void testArray() {
    String result;
    Integer i1 = new Integer(1);
    Integer i2 = new Integer(2);
    Integer i3 = new Integer(3);
    
    Integer[] ia = new Integer[] {i1, i2, i3};

    result = MessageFormatter.arrayFormat("Value {} is smaller than {} and {}.", ia);
    assertEquals("Value 1 is smaller than 2 and 3.", result);
    
    result = MessageFormatter.arrayFormat("{}{}{}", ia);
    assertEquals("123", result);
    
    result = MessageFormatter.arrayFormat("Value {} is smaller than {}.", ia);
    assertEquals("Value 1 is smaller than 2.", result);
    
    result = MessageFormatter.arrayFormat("Value {} is smaller than {}", ia);
    assertEquals("Value 1 is smaller than 2", result);
   
    result = MessageFormatter.arrayFormat("Val={}, {, Val={}", ia);
    assertEquals("Val=1, {, Val={}", result);
   
    result = MessageFormatter.arrayFormat("Val={}, \\{, Val={}", ia);
    assertEquals("Val=1, {, Val=2", result);
   
    
    result = MessageFormatter.arrayFormat("Val1={}, Val2={", ia);
    assertEquals("Val1=1, Val2={", result);
    

  }

}
