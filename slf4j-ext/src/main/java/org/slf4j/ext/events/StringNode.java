package org.slf4j.ext.events;

/**
 * @author Himanshu Vijay
 */
public class StringNode extends LeafNode<String> {
    public StringNode(String name, Node parent, String defaultValue){
        super(name, parent, defaultValue);
    }
}
