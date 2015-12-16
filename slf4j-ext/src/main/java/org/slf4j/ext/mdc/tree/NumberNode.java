package org.slf4j.ext.mdc.tree;

/**
 * @author Himanshu Vijay
 */
public class NumberNode extends LeafNode<NumberNode, Number> {
    public NumberNode(String name, Node parent, Number defaultValue){
        super(name, NumberNode.class, parent, Number.class, defaultValue);
    }

  @Override
  public NumberNode copy(Node parent) {
    NumberNode copy = new NumberNode(this.NAME, null, this.DEFAULT_VALUE);
    copy.set(this.value);
    copy.setParent(parent);
    return copy;
  }
}
