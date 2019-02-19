package org.slf4j.spi;

@FunctionalInterface
public interface TriConsumer<A, B, C> {

  void accept(A a, B b, C c);

}
