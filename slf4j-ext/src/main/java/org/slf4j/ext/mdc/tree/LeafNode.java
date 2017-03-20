package org.slf4j.ext.mdc.tree;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
public abstract class LeafNode<T extends LeafNode, P> extends Node<T> {
  private final Class<T> clazz;
  private final Class<P> valueClazz;
  protected final P DEFAULT_VALUE;
  protected P value;

  protected LeafNode(String name, Class<T> nodeType, Node parent, Class<P> valueType, P defaultValue) {
    super(name, nodeType, parent);
    this.clazz = nodeType;
    this.valueClazz = valueType;
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

  @Override
  public T copy(Node parent) {
    try {
      Constructor<T> constructor = clazz.getDeclaredConstructor(String.class, Node.class, valueClazz);
      constructor.setAccessible(true);
      T copy = constructor.newInstance(this.NAME, parent, this.DEFAULT_VALUE);
      copy.set(this.value);
      return copy;
    } catch (Exception e) {//This should never happen
      return (T)this;
    }
  }
}
