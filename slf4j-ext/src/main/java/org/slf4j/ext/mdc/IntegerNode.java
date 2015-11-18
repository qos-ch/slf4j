package org.slf4j.ext.mdc;

/**
 * @author Himanshu Vijay
 */
public class IntegerNode extends LeafNode<IntegerNode, Integer> {
    public IntegerNode(String name, Node parent, Integer defaultValue){
        super(name, parent, defaultValue);
    }

  @Override
  public IntegerNode copy(Node parent) {
    IntegerNode copy = new IntegerNode(this.NAME, null, this.DEFAULT_VALUE);
    copy.set(this.value);
    copy.setParent(parent);
    return copy;
  }
}
