package org.slf4j.ext.mdc.tree;

/**
 * @author Himanshu Vijay
 */
public class BooleanNode extends LeafNode<BooleanNode, Boolean> {
    public BooleanNode(String name, Node parent, Boolean defaultValue){
        super(name, BooleanNode.class, parent, Boolean.class, defaultValue);
    }

  @Override
  public BooleanNode copy(Node parent) {
    BooleanNode copy = new BooleanNode(this.NAME, null, this.DEFAULT_VALUE);
    copy.set(this.value);
    copy.setParent(parent);
    return copy;
  }
}
