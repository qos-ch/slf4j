package org.slf4j.ext.events;

/**
 * @author Himanshu Vijay
 */
public class IntegerNode extends LeafNode<Integer> {
    public IntegerNode(String name, Node parent, Integer defaultValue){
        super(name, parent, defaultValue);
    }
}
