package org.slf4j.ext.mdc.processor;

import javax.lang.model.element.Element;

public class ProcessingException extends Exception{
  private Element element;

  public ProcessingException(Element e, String msg, Object... args){
    super(String.format(msg, args));
    element = e;
  }

  public Element getElement() {
    return element;
  }
}