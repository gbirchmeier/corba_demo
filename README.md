Corba demo stuff
================

Some corba demo apps.


Ruby and CORBA
--------------
Ruby apps were developed with JRuby and R2Corba's JRuby libraries.

(They might work with regular Ruby and native compiled R2Corba.)

To install R2Corba for JRuby

* [go here](https://osportal.remedy.nl/projects/r2corba/files) and download the latest `prebuilt_java` archive
* Open it and follow the instructions in the `INSTALL` file
  * These instructions will install it into your rvm install dirs
* When done, you can delete the archive.


Java and CORBA
--------------
This **Echo** demo contains a Java client.
[This page](http://docs.oracle.com/javase/6/docs/technotes/guides/idl/tutorial/GSIDL.html)
was quite helpful when I was writing it.

The client can talk to the Ruby server.  I didn't write a Java server.

Use the shell scripts to build, run, and clean.
