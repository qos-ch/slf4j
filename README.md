# SLF4J Android

The motivation for the SLF4J Android project was to ease using existing libraries
which use SLF4J as their logging framework on the [Google Android platform](http://developer.android.com).

This project is a basic implementation that simply forwards all SLF4J log requests to the
[logger](http://developer.android.com/reference/android/util/Log.html) provided by the Google Android platform.

As slf4j-jdk14, slf4j-log4j etc. slf4j-android is a maven module that depends on slf4j-api only
so it can easily be built with maven and synced with [the original slf4j codebase](https://github.com/qos-ch/slf4j).

### Status
The implementation is currently fully functional and has been fully tested in a
production environment.