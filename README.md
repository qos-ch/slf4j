# About SLF4J
The Simple Logging Facade for Java (SLF4J) serves as a simple facade or abstraction for various logging frameworks (e.g. java.util.logging, logback, log4j) allowing the end user to plug in the desired logging framework at deployment time.
More information can be found on the [SLF4J website](http://www.slf4j.org).

# Build Status
[![Build Status](https://travis-ci.org/qos-ch/slf4j.png)](https://travis-ci.org/qos-ch/slf4j)

# Building locally
SLF4J uses Java 9 to build, and Java 6 to run the tests.

1. To build, SLF4J uses the Java 9 JDK. Just install it normally if you haven't already.
2. For automatic testing, SLF4J uses Java 6. If your usual source of Java installers does not carry this anymore, you can grab it from from Oracle's "Java Archive" page. Linux users can use the `.bin` installer: it will unpack to a local directory without any administrator rights, which is fine for Maven use.
3. To tell Maven that you have two toolchains, create `.m2/toolchains.xml` with the following content:

```xml
<toolchains>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>1.6</version>
    </provides>
    <configuration>
      <jdkHome>/home/jo/bin/jdk1.6.0_45</jdkHome>
    </configuration>
  </toolchain>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>9</version>
    </provides>
    <configuration>
      <jdkHome>/usr/lib/jvm/java-9-openjdk-amd64</jdkHome>
    </configuration>
  </toolchain>
</toolchains>
```

`/home/jo/bin/jdk1.6.0_45` is a typical local-file install, `/usr/lib/jvm/java-9-openjdk-amd64` a typical system-wide install; replace that with the locations of your own JDK installs.  
_Note to Linux users:_ Maven does not understand the `~` shorthand for your home directory.  
_Note:_ You need to use __1.6__ for Java 6; this is technically wrong, but what the build expects. __9__ for Java 9 is fine.

# How to contribute pull requests
If you are interested in improving SLF4J, great! The SLF4J community looks forward to your contribution. Please follow this process:

1. Start a discussion on the [slf4j-dev mailing list](http://www.slf4j.org/mailing-lists.html) about your proposed change. Alternately file a [bug report](http://www.slf4j.org/bug-reporting.html).
2. Fork qos-ch/slf4j. Ideally, create a new branch from your fork for your contribution to make it easier to merge your changes back.
3. Make your changes on the branch you hopefuly created in Step 2. Be sure that your code passes existing unit tests. Please add unit tests for your work if appropriate. It usually is.
4. Push your changes to your fork/branch in github. Don't push it to your master! If you do it will make it harder to submit new changes later.
5. Submit a pull request to SLF4J from from your commit page on github.
