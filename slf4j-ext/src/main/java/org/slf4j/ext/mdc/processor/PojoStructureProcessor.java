package org.slf4j.ext.mdc.processor;


import org.slf4j.ext.mdc.annotation.Pojo;
import org.slf4j.ext.mdc.annotation.Property;
import org.slf4j.ext.mdc.annotation.RootPojo;
import org.slf4j.ext.mdc.tree.RootNode;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.LinkedHashSet;
import java.util.Set;
//*** Need to figure out *** maven dependency is not fetching the Autoservice repo

@com.google.auto.service.AutoService(Processor.class)
public class PojoStructureProcessor extends AbstractProcessor{

  private int level = 1;
  private Types typeUtils;
  private Elements elementUtils;
  private Filer filer;
  private Messager messsager;
  private RootNode tree;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment){
    super.init(processingEnvironment);
    typeUtils = processingEnvironment.getTypeUtils();
    elementUtils = processingEnvironment.getElementUtils();
    filer = processingEnvironment.getFiler();
    messsager = processingEnvironment.getMessager();
    //System.out.println("**************************************");
  }

  @Override
  public SourceVersion getSupportedSourceVersion(){
    return SourceVersion.latestSupported();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes(){
    Set<String> annotations = new LinkedHashSet<String>();
    annotations.add(Pojo.class.getCanonicalName());
    annotations.add(RootPojo.class.getCanonicalName());
    annotations.add(Property.class.getCanonicalName());
    return annotations;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
    Set rootElementSet = roundEnvironment.getElementsAnnotatedWith(RootPojo.class);
    TypeElement rootElement = (TypeElement)rootElementSet.iterator().next();
    if ( rootElementSet.size() > 1 ){
      try {
        throw new ProcessingException(rootElement, "Only one class can be annotated with @%s", RootPojo.class.getSimpleName());
      } catch (ProcessingException e) {
        error(e.getElement(), e.getMessage());
      }
    }
    try {
      System.out.println(rootElement.getSimpleName()+ "--> Root");
      iterateElement(rootElement, level);
    } catch (ProcessingException e) {
      error(e.getElement(),"Annotation processor error");
    }
    return true;
  }

  public void iterateElement(Element element, int level) throws ProcessingException {
    for(Element e :element.getEnclosedElements()){
      if(e.getAnnotation(Property.class) != null){
        Element e1 = ((DeclaredType)e.asType()).asElement();
        if((e1.getAnnotation(Pojo.class) != null)){
          printSpaces(level);
          System.out.println(e1.getSimpleName()+" --> non leaf node");
          iterateElement(e1, level+1);
        } else if (e1.getAnnotation(RootPojo.class) != null){
          throw new ProcessingException(e1, " Only one root is allowed.");
        } else {
          printSpaces(level);
          System.out.println(e.getSimpleName() + " --> leaf node");
        }
      }
    }
  }

  public void printSpaces(int n){
    for(int i=0;i<n;i++){
      System.out.print(" ");
    }
  }
  public void error(Element e, String msg){
    messsager.printMessage(Diagnostic.Kind.ERROR, msg, e);
  }
}