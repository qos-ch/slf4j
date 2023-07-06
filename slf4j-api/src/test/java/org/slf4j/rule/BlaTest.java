package org.slf4j.rule;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

@Ignore // this test has intentional fails
public class BlaTest {

    @Rule
    public RunInNewThreadRule runInThread = new RunInNewThreadRule();

    @Test
    public void aTest() {
        System.out.println("running aTest in "+ Thread.currentThread().getName());
    }
    
    @RunInNewThread
    @Test
    public void bTest() {
        System.out.println("running bTest in "+ Thread.currentThread().getName());
    }
  
    @RunInNewThread(timeout = 2000L)
    @Test
    public void cTest() {
        System.out.println("running cTest in "+ Thread.currentThread().getName());
    }
    @RunInNewThread()
    @Test
    public void dTest() {
        System.out.println("running dTest in "+ Thread.currentThread().getName());
        fail();
    }
}

