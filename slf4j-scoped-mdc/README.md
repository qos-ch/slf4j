# slf4j-scoped-mdc

Scoped diagnostic context for virtual threads and structured concurrency. The API follows logback's `ScopedMDC`, but the storage is a `java.util.ServiceLoader` service. The module has no dependency on Logback.

The built-in provider stores the map in `java.lang.ScopedValue` (Java 25+). Values are inherited by tasks forked from `StructuredTaskScope`. They are not inherited by threads started with the `Thread` API.

## Maven

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-scoped-mdc</artifactId>
    <version>${project-version}</version>
</dependency>
```

Requires JDK 25 or later. `ScopedValue` is final in Java 25, so the module does not need `--enable-preview` at runtime. The reactor build includes this module only when Maven itself runs on JDK 25+.

## Usage

```java
ScopedMDC.put("requestId", "abc-123")
         .put("userId", "user-42")
         .run(() -> {
             logger.info("Processing request");
         });
```

`putAll(Map)` adds several entries. `Binding.call(CallableOp)` returns a value and may throw a checked exception. A nested scope inherits the enclosing entries, may override them, and restores the enclosing scope when it exits.

Read the current scope with `ScopedMDC.get(key)` or `ScopedMDC.getPropertyMap()`. Code that formats log output should use those methods. The built-in `ScopedValue` is not part of the API.

## Replacing the provider

Implement `org.slf4j.scoped.spi.ScopedMDCAdapter` and list the class, which must have a public no-argument constructor, in

```
META-INF/services/org.slf4j.scoped.spi.ScopedMDCAdapter
```

`put` and `putAll` return a `ScopedMDCAdapter.Binding`. Copy the current scope in `put` and `putAll`, then add the new entries. `Binding.put` adds to that binding only. `Binding.run` and `Binding.call` install the binding's map for the dynamic extent of the operation and restore the previous map afterwards, including when the operation throws.

`DefaultScopedMDCAdapter` is not listed as a service. It is used when `ServiceLoader` finds no provider. When several providers are found, the first one is used and a warning is written to the SLF4J internal report stream.

To skip `ServiceLoader`, set the system property `slf4j.scopedMDCAdapter` to the provider class name.
