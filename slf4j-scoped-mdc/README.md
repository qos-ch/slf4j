# slf4j-scoped-mdc

Scoped diagnostic context for virtual threads and structured concurrency. Values are stored in `java.lang.ScopedValue` (Java 25+), so they are inherited by tasks forked from `StructuredTaskScope` and are not inherited by threads started with the `Thread` API.

## Maven

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-scoped-mdc</artifactId>
    <version>${project-version}</version>
</dependency>
```

Requires JDK 25 or later. `ScopedValue` is final in Java 25, so the module does not need `--enable-preview` at runtime. The reactor build includes this module only when Maven itself runs on JDK 25+.

The modular jar name is `org.slf4j.scoped.mdc`. It exports `org.slf4j.scoped`.

## Usage

```java
ScopedMDC.put("requestId", "abc-123")
         .put("userId", "user-42")
         .run(() -> {
             logger.info("Processing request");
         });
```

`putAll(Map)` adds several entries. `Binding.call` returns a value and may throw a checked exception. A nested scope inherits the enclosing entries, may override them, and restores the enclosing scope when it exits.

Read the current scope with `ScopedMDC.get(key)` or `ScopedMDC.getPropertyMap()`.
