
This directory is used to test the module against various log4j calls. 
Two test cases simulate the typical calls that one can find in an application 
that uses either log4j 1.2.x, or log4j 1.3.x.

In the same directory is a build.xml file that uses ant to 
compile the test cases with the corresponding log4j version, 
and to run these tests without log4j in the classpath but with
logback jars instead.

To run the tests, one must have ant installed. Issuing the following command, 
once in the compatibility directory will launch the tests:

ant all

To obtain more information about the use of the log4j-over-slf4j module, 
please visit http://www.slf4j.org/log4j-over-slf4j.html
