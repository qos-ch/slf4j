package org.slf4j.ext.events;

/**
 * Class to represent leaf nodes of your POJO tree. For optional use.
 * Sub classes of this class handle special conditions like Nulls.
 *
 * Suppose your POJO tree is big and you need some special handling that wherever
 *
 * If you are using code generators to generate your POJO structure from a template, then you'd probably need to do update
 * that code to handle special conditions wherever.
 *
 * @author Himanshu Vijay
 */
public abstract class LeafNode<T> extends Node {
    private final T DEFAULT_VALUE;
    private T value;

    protected LeafNode(String name, Node parent, T defaultValue){
        super(name, parent);
        this.DEFAULT_VALUE = defaultValue;
    }

    @Override
    public void setToDefault(){
        value = DEFAULT_VALUE;
    }

    public T get(){
        return value;
    }

    /**
     * Sets value only if param t is not null. Override for different behavior.
     * @param t
     */
    public void set(T t){
        if(t != null){
            value = t;
        }
    }
}
