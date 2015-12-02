package org.slf4j.ext.mdc.tree;

/**
 * @author Himanshu Vijay
 */
public class StringNode extends LeafNode<StringNode, String> {
  public StringNode(String name, Node parent, String defaultValue) {
    super(name, parent, defaultValue);
  }

  @Override
  public StringNode copy(Node parent) {
    StringNode copy = new StringNode(this.NAME, null, this.DEFAULT_VALUE);
    copy.set(this.value);
    copy.setParent(parent);
    return copy;
  }
}
