# Java Sound Server

Java-Sound-Server is an audio output "Speaker" server that uses the Java Sound API.

## Specification

[Version 1.0.0](specification_v1_0_0.md)

## Clients

[Common Lisp](https://github.com/Frechmatz/cl-java-sound-client)

## Build and start

The server requires Java version 11 or greater.

**Build**

`mvn clean install`

**Run**

Start server on default port 9000

`mvn exec:java`

Start server on port 8888

`mvn exec:java -Dexec.args="8888"`

