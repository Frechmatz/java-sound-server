# Java Sound Server

Java-Sound-Server is an audio output "Speaker" server that uses the Java Sound API. 
The server opens a port to which clients can connect and play audio.
Audio data consists of PCM samples that are rendered by the client and sent to the server.

## Specification

[Version 1.0.0](specification_v1_0_0.md)

## Clients

[Common Lisp](https://github.com/Frechmatz/cl-java-sound-client)

## Build

Requires Java 11 or greater.

`mvn clean install`

## Start

Start server and accept connections on default port 9000

`mvn exec:java`

Start server and accept connections on port 8888

`mvn exec:java -Dexec.args="8888"`

