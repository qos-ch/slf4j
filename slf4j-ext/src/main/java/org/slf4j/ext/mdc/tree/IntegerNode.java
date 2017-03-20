package org.slf4j.ext.mdc.tree;

/**
 * @author Himanshu Vijay
 */
public class IntegerNode extends LeafNode<IntegerNode, Integer> {
    public IntegerNode(String name, Node parent, Integer defaultValue){
        super(name, IntegerNode.class, parent, Integer.class, defaultValue);
    }

  @Override
  public IntegerNode copy(Node parent) {
    IntegerNode copy = new IntegerNode(this.NAME, null, this.DEFAULT_VALUE);
    copy.set(this.value);
    copy.setParent(parent);
    return copy;
  }
}
