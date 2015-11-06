package org.slf4j.ext.events;

/**
 * @author Himanshu Vijay
 */
public abstract class Node implements Cloneable {
    protected final String NAME;
    protected final String FQN;
    private final Node PARENT;

    protected Node(String name, Node parent){
        this.NAME = name;
        this.PARENT = parent;
        if(PARENT != null){
            this.FQN = String.format("%s.%s", PARENT.FQN, NAME);
        } else {
            this.FQN = NAME;
        }
    }

    public abstract void setToDefault();
}
