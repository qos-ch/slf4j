package org.slf4j.ext.mdc;

/**
 * @author Himanshu Vijay
 */
public abstract class Node<T> implements Cloneable {
  protected final String NAME;
  protected final String FQN;
  private Node parent;

  protected Node(String name, Node parent) {
    this.NAME = name;
    this.parent = parent;
    if(this.parent != null) {
      this.FQN = String.format("%s.%s", this.parent.FQN, NAME);
    } else {
      this.FQN = NAME;
    }
  }

  protected void setParent(Node parent) {
    this.parent = parent;
  }

  public abstract void setToDefault();

  public T copy(){
    return copy(null);
  }

  protected abstract T copy(Node parent);

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return copy();
  }
}