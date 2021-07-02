# About SLF4J
The Simple Logging Facade for Java (SLF4J) serves as a simple facade or abstraction for various logging frameworks (e.g. java.util.logging, logback, log4j) allowing the end user to plug in the desired logging framework at deployment time.
More information can be found on the [SLF4J website](http://www.slf4j.org).
# Build Status
[![Build Status](https://travis-ci.org/qos-ch/slf4j.svg)](https://travis-ci.org/qos-ch/slf4j)


# How to build SLF4J

SLF4J uses Maven as its build tool.

All versions upto and including 1.7.x require Java 5 or later to
build. SLF4J version 2.0.x requires Java 9 or later.

# How to contribute pull requests

If you are interested in improving SLF4J, that is great! The SLF4J
community looks forward to your contribution. Please follow this
process:

1. Start a discussion on the [slf4j-dev mailing
list](http://www.slf4j.org/mailing-lists.html) about your proposed
change. Alternately, file a [bug
report](http://www.slf4j.org/bug-reporting.html) to initiatite the
discussion. Note that we ask pull requests to be linked to a [Jira
ticket](https://jira.qos.ch/).

2. Fork qos-ch/slf4j. Ideally, create a new branch from your fork for
your contribution to make it easier to merge your changes back.

3. Make your changes on the branch you hopefuly created in Step 2. Be
sure that your code passes existing unit tests. Please add unit tests
for your work if appropriate. It usually is.

4. All commits must have signed off by the contributor attesting to
[Developer Certificate of Origin (DCO)](https://developercertificate.org/).

5. Push your changes to your fork/branch in github. Don't push it to
your master! If you do it will make it harder to submit new changes
later.

6. Submit a pull request to SLF4J from from your commit page on github.

7. Did we mention that you will be asked to link your pull request
with a Jira ticket?

