package org.slf4j.ext.mdc.tree;

/**
 * Class to represent leaf nodes of your POJO tree. For optional use.
 * Sub classes of this class handle special conditions like Nulls.
 * <p/>
 * Suppose your POJO tree is big and you need some special handling that wherever
 * <p/>
 * If you are using code generators to generate your POJO structure from a template, then you'd probably need to do update
 * that code to handle special conditions wherever.
 *
 * @author Himanshu Vijay
 */
public abstract class LeafNode<T, P> extends Node<T> {
  protected final P DEFAULT_VALUE;
  protected P value;

  protected LeafNode(String name, Node parent, P defaultValue) {
    super(name, parent);
    this.DEFAULT_VALUE = defaultValue;
  }

  @Override
  public void setToDefault() {
    value = DEFAULT_VALUE;
  }

  public P get() {
    return value;
  }

  /**
   * Sets value only if param t is not null. Override for different behavior.
   *
   * @param t
   */
  public void set(P t) {
    if(t != null) {
      value = t;
    }
  }
}
