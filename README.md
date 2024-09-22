# About SLF4J

The Simple Logging Facade for Java (SLF4J) serves as a simple facade
or abstraction for various logging frameworks (e.g. java.util.logging,
logback, reload4j, log4j 2.x, logevents, penna, rainbowgum, tinylog)
allowing the end user to plug in the desired logging framework at
deployment time.

More information can be found on the [SLF4J website](http://www.slf4j.org).

# Search org.slf4j artifacts on Maven Central
[![Maven Central](https://img.shields.io/badge/Search%20org%2Eslf4j%20artifacts%20on%20Maven%20Central-2.0.x-green)](https://central.sonatype.com/search?namespace=org.slf4j) 


# In case of problems

In case of problems please do not hesitate to post an e-mail message
on the [slf4j-user@qos.ch](https://mailman.qos.ch/cgi-bin/mailman/listinfo/slf4j-user)
mailing list or to start a <a
href="https://github.com/qos-ch/slf4j/discussions">discussion</a> on
github.  However, please do not directly e-mail SLF4J developers. The
answer to your question might be useful to other users. Moreover,
there are many knowledgeable users on the slf4j-user mailing lists who
can quickly answer your questions.

# Urgent issues

For urgent issues do not hesitate to [champion a
release](https://github.com/sponsors/qos-ch/sponsorships?tier_id=77436).
In principle, most championed issues are solved within 3 business days
ensued by a release.

# How to build SLF4J

SLF4J uses Maven as its build tool.

SLF4J version 2.0.x will run under Java 8 but requires Java 9 or later to build.

# How to contribute pull requests

If you are interested in improving SLF4J, that is great! The SLF4J
community looks forward to your contribution. Please follow this
process:

1. Start a discussion on the [slf4j-dev mailing
list](http://www.slf4j.org/mailing-lists.html) about your proposed
change. Alternately, file a [bug
report](https://github.com/qos-ch/slf4j/issues) on github to initiate the
discussion. 

2. Fork qos-ch/slf4j. Ideally, create a new branch from your fork for
your contribution to make it easier to merge your changes back.

3. Make your changes on the branch created in Step 2. Be
sure that your code passes existing unit tests. Please add unit tests
for your work if appropriate. It usually is.

4. All commits must have signed off by the contributor attesting to
[Developer Certificate of Origin
(DCO)](https://developercertificate.org/). Commits without sign off
will be automatically rejected by the [DCO GitHub
check](https://probot.github.io/apps/dco/) application.

5. Push your changes to your fork/branch in GitHub. Don't push it to
your master! If you do, it will make it harder to submit new changes
later.

6. Submit a pull request to SLF4J from your commit page on GitHub.

