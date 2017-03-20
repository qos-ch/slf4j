package org.slf4j.ext.mdc;

import org.junit.Test;
import org.slf4j.ext.mdc.tree.Node;
import org.slf4j.ext.mdc.example2.Event;

import static org.junit.Assert.assertEquals;

/**
 * @author Himanshu Vijay
 */
public class NodeTest {
    class TestNode extends Node<TestNode> {
        public TestNode(String name, Node parent){
            super(name, TestNode.class, parent);
        }

        @Override
        public void setToDefault() {}

        @Override
        public TestNode copy(Node parent) {
            return null;//We don't care for this test.
        }

        public String fqn(){
            return this.FQN;//Exposed for testing
        }
    }

    @Test
    public void testFqnWhenNoParent(){
        TestNode n1 = new TestNode("root", null);
        assertEquals("root", n1.fqn());
    }

    @Test
    public void testFqnWhenParent(){
        TestNode n1 = new TestNode("root", null);
        TestNode n2 = new TestNode("child1", n1);
        assertEquals("root.child1", n2.fqn());
        TestNode n3 = new TestNode("grandChild1", n2);
        assertEquals("root.child1.grandChild1", n3.fqn());
    }
}
