package org.slf4j.ext.mdc.processor;


import org.slf4j.ext.mdc.annotation.Pojo;
import org.slf4j.ext.mdc.annotation.Property;
import org.slf4j.ext.mdc.annotation.RootPojo;
import org.slf4j.ext.mdc.tree.Node;
import org.slf4j.ext.mdc.tree.NonLeafNode;
import org.slf4j.ext.mdc.tree.RootNode;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import org.slf4j.ext.mdc.tree.StringNode;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

@AutoService(Processor.class)
public class PojoStructureProcessor extends AbstractProcessor{

  private int level = 1;
  private Types typeUtils;
  private Elements elementUtils;
  private Filer filer;
  private Messager messsager;
  private Node rootNode;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment){
    super.init(processingEnvironment);
    typeUtils = processingEnvironment.getTypeUtils();
    elementUtils = processingEnvironment.getElementUtils();
    filer = processingEnvironment.getFiler();
    messsager = processingEnvironment.getMessager();
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
      generateTreeCode(rootElement, elementUtils, filer);
    } catch (ProcessingException e) {
      error(e.getElement(), "Annotation processor error");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  private void generateTreeCode(Element root, Elements elementUtils, Filer filer) throws IOException, ProcessingException {
    TypeElement classNameElement = elementUtils.getTypeElement(root.asType().toString());
    String newClassName = classNameElement.getSimpleName().toString();
    PackageElement pkg = elementUtils.getPackageOf(classNameElement);
    String pkgName = pkg.isUnnamed()? null: pkg.getQualifiedName().toString();
    MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE);
    constructorBuilder.addStatement("super(\"" + classNameElement.getSimpleName().toString().toUpperCase() + "\",\"" + classNameElement.getSimpleName().toString().toLowerCase() + "\")");
    MethodSpec.Builder copyMethodBuilder = MethodSpec.methodBuilder("copy")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(TypeName.get(root.asType()))
            .addStatement(root.getSimpleName().toString() + " copy = new " + root.getSimpleName().toString() + "()");

    ClassName parClass = ClassName.get(pkgName, newClassName);
    ClassName rootNodeClass = ClassName.get("org.slf4j.ext.mdc.tree", "RootNode");
    TypeName superRootNode = ParameterizedTypeName.get(rootNodeClass, parClass);

    TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(newClassName)
            .superclass(superRootNode)
            .addMethod(constructorBuilder.build());

    MethodSpec.Builder setDefaultMethodBuilder = MethodSpec.methodBuilder("setToDefault")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class);

    generateClassTree(root, typeSpecBuilder, copyMethodBuilder, setDefaultMethodBuilder);

    typeSpecBuilder.addMethod(MethodSpec.methodBuilder("newInstance")
                                      .addModifiers(Modifier.PROTECTED, Modifier.STATIC)
                                      .returns(RootNode.class)
                                      .addStatement("return new " + root.getSimpleName().toString() + "()")
                                      .build());

    typeSpecBuilder.addMethod(MethodSpec.methodBuilder("get")
                                      .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                      .returns(TypeName.get(root.asType()))
                                      .addStatement("return (" + root.getSimpleName().toString() + ")RootNode.get()")
                                      .build());


    typeSpecBuilder.addMethod(setDefaultMethodBuilder.build());
    typeSpecBuilder.addMethod(copyMethodBuilder.build());
    JavaFile.builder(pkgName, typeSpecBuilder.build())
            .build()
            .writeTo(filer);

  }

  private void generateClassTree(Element root, TypeSpec.Builder typeSpecBuilder,
                            MethodSpec.Builder copyMethodBuilder, MethodSpec.Builder setDefaultMethodBuilder ) throws ProcessingException {
    for(Element e :root.getEnclosedElements()){
      if(isProperty(e)){
        Element e1 = ((DeclaredType)e.asType()).asElement();
        if(isPojoType(e1)){
          typeSpecBuilder.addField(FieldSpec.builder(TypeName.get(e.asType()), e.getSimpleName().toString(), Modifier.PRIVATE)
                                           .initializer("new $T($S,this)", TypeName.get(e.asType()), e.getSimpleName().toString())
                                           .build());
          typeSpecBuilder.addMethod(MethodSpec.methodBuilder("get" + toProperCase(e.getSimpleName().toString()))
                                            .addModifiers(Modifier.PUBLIC)
                                            .returns(TypeName.get(e.asType()))
                                            .addStatement("return this." + e.getSimpleName().toString())
                                            .build());

          typeSpecBuilder.addMethod(MethodSpec.methodBuilder("set" + toProperCase(e.getSimpleName().toString()))
                                            .addModifiers(Modifier.PUBLIC)
                                            .addParameter(TypeName.get(e.asType()), e.getSimpleName().toString())
                                            .addStatement("this." + e.getSimpleName().toString() + " = " + e.getSimpleName().toString())
                                            .build());

          copyMethodBuilder.addStatement("copy.set" + toProperCase(e.getSimpleName().toString()) + "(" + e.getSimpleName().toString() + ".copy())");
          setDefaultMethodBuilder.addStatement(e.getSimpleName().toString()+".setToDefault()");
        } else if (isRootElement(e1)){
          throw new ProcessingException((TypeElement)e1, " Only one root is allowed.");
        } else {
          String eleType  = e.asType().toString();
          String literal = (eleType.contains("String")) ? "$S" : ((eleType.contains("Boolean")) ? "$N" : "$L");
          typeSpecBuilder.addField(FieldSpec.builder(getElementToNodeType(e), e.getSimpleName().toString(), Modifier.PRIVATE)
                                           .initializer("new $T($S, this, " + literal + ")"
                                                   , getElementToNodeType(e), e.getSimpleName().toString()
                                                   , getElementDefaultValue(e))
                                           .build());

          typeSpecBuilder.addMethod(MethodSpec.methodBuilder("get" + toProperCase(e.getSimpleName().toString()))
                                            .addModifiers(Modifier.PUBLIC)
                                            .returns(getElementToNodeType(e))
                                            .addStatement("return this." + e.getSimpleName().toString())
                                            .build());

          typeSpecBuilder.addMethod(MethodSpec.methodBuilder("set" + toProperCase(e.getSimpleName().toString()))
                                            .addModifiers(Modifier.PUBLIC)
                                            .addParameter(getElementToNodeType(e), e.getSimpleName().toString())
                                            .addStatement("this." + e.getSimpleName().toString() + " = " + e.getSimpleName().toString())
                                            .build());

          copyMethodBuilder.addStatement("copy.set" + toProperCase(e.getSimpleName().toString()) + "(this."+ e.getSimpleName().toString()+")");
        }
      } else if (isPojo(e)){
        ClassName parClass = ClassName.get("org.slf4j.ext.mdc.example2", e.getSimpleName().toString());
        ClassName nonLeafNodeClass = ClassName.get("org.slf4j.ext.mdc.tree", "NonLeafNode");
        TypeName nonLeafNode = ParameterizedTypeName.get(nonLeafNodeClass, parClass);
        MethodSpec.Builder innerConstructorBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE);
        innerConstructorBuilder.addParameter(String.class, "name")
                .addParameter(Node.class, "parent")
                .addStatement("super(name, parent)");

        MethodSpec.Builder innerSetDefaultMethodBuilder = MethodSpec.methodBuilder("setToDefault")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class);
        MethodSpec.Builder innerCopyMethodBuilder = MethodSpec.methodBuilder("copy")
                .addParameter(Node.class, "parent")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addStatement(e.getSimpleName() + " copy = new " + e.getSimpleName() + "(this.NAME, null)")
                .addStatement("copy.setParent(parent)")
                .returns(TypeName.get(e.asType()));
        TypeSpec.Builder innerTypeSpecBuilder = TypeSpec.classBuilder(e.getSimpleName().toString())
                .addMethod(innerConstructorBuilder.build())
                .addModifiers(Modifier.PUBLIC)
                .superclass(nonLeafNode);
        generateClassTree(e, innerTypeSpecBuilder, innerCopyMethodBuilder, innerSetDefaultMethodBuilder);
        innerTypeSpecBuilder.addMethod(innerSetDefaultMethodBuilder.build())
                .addMethod(innerCopyMethodBuilder.build());
        typeSpecBuilder.addType(innerTypeSpecBuilder.build());
      }
    }
    copyMethodBuilder.addStatement("return copy");
  }

  private boolean isRootElement(Element rootElement){
    if(rootElement.getAnnotation(RootPojo.class) != null){
      return true;
    }
    return false;
  }

  private boolean isPojo(Element element){
    if((element.getAnnotation(Pojo.class) != null)){
      return true;
    }
    return false;
  }

  private boolean isPojoType(Element element){
    if(((DeclaredType)(element.asType())).asElement().getAnnotation(Pojo.class) != null){
      return true;
    }
    return false;
  }

  private boolean isProperty(Element element){
    if(element.getAnnotation(Property.class) != null){
      return true;
    }
    return false;
  }

  public static String toProperCase(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }

  private ClassName getElementToNodeType(Element e){
    ClassName entityClassName = null;
    String pkg = "org.slf4j.ext.mdc.tree";
    if(e.asType().toString().contains("String")){
      entityClassName = ClassName.get(pkg, "StringNode");
    } else if(e.asType().toString().contains("Boolean")){
      entityClassName = ClassName.get(pkg, "BooleanNode");
    } else if(e.asType().toString().contains("int") ||
            e.asType().toString().contains("Number")){
      entityClassName = ClassName.get(pkg, "IntegerNode");
    }
    return entityClassName;
  }

  private String getElementDefaultValue(Element e){
    if(e.asType().toString().contains("String")){
      return "";
    } else if(e.asType().toString().contains("Boolean")){
      return "true";
    } else if(e.asType().toString().contains("Number")){
      return "0";
    }else if(e.asType().toString().contains("int")){
      return "0";
    }
    return null;
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