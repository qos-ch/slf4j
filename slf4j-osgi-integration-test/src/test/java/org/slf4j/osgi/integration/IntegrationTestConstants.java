/* 
 * Copyright (c) 2004-2005 SLF4J.ORG
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
package org.slf4j.osgi.integration;

public interface IntegrationTestConstants {
    
    /**
     * Versions of this release.
     */
    public static final String SLF4J_VERSION_UNDER_TEST = "1.3.0-SNAPSHOT";
    
    public static final String SLF4J_GROUP_ID = "org.slf4j";
    
    public static final String SPRINGFRAMEWORK_OSGI_GROUP_NAME = "org.springframework.osgi";
    

    public static final String JCL104_ADAPTER_BUNDLE_ARTIFACT_ID = "jcl104-over-slf4j";
    
    public static final String LOGSERVICE_ADAPTER_BUNDLE_ARTIFACT_ID = "osgi-over-slf4j";

    public static final String JDK14_BINDING_BUNDLE_ARTIFACT_ID = "slf4j-jdk14";

    public static final String SIMPLE_BINDING_BUNDLE_ARTIFACT_ID = "slf4j-simple";

    public static final String LOG4J12_BINDING_BUNDLE_ARTIFACT_ID = "slf4j-log4j12";

    public static final String NOP_BINDING_BUNDLE_ARTIFACT_ID = "slf4j-nop";

    public static final String TEST_BUNDLE_ARTIFACT_ID = "slf4j-osgi-test-bundle";

    
    public static final String TEST_BUNDLE_SYM_NAME = SLF4J_GROUP_ID+'.'+TEST_BUNDLE_ARTIFACT_ID;
    
	public static final String NOP_BINDING_BUNDLE_SYM_NAME = SLF4J_GROUP_ID+'.'+NOP_BINDING_BUNDLE_ARTIFACT_ID;
   
	public static final String LOG4J_BINDING_BUNDLE_SYM_NAME = SLF4J_GROUP_ID+'.'+LOG4J12_BINDING_BUNDLE_ARTIFACT_ID;
	
	public static final String SIMPLE_BINDING_BUNDLE_SYM_NAME = SLF4J_GROUP_ID+'.'+SIMPLE_BINDING_BUNDLE_ARTIFACT_ID;
    
    public static final String JDK_BINDING_BUNDLE_SYM_NAME = SLF4J_GROUP_ID+'.'+JDK14_BINDING_BUNDLE_ARTIFACT_ID;
	
	public static final String JCL_ADAPTER_BUNDLE_SYM_NAME = SLF4J_GROUP_ID+'.'+JCL104_ADAPTER_BUNDLE_ARTIFACT_ID;
    
    public static final String LOGSERVICE_ADAPTER_BUNDLE_SYM_NAME = SLF4J_GROUP_ID+'.'+LOGSERVICE_ADAPTER_BUNDLE_ARTIFACT_ID;
   
    
    
}
