# java-sound-server

An experimental Audio-Output Server using the Java Sound API. Opens a port to which a client can connect and play audio.

The protocol as well as the represention of messages are work in progress.

The general procedure is as follows:

* Client establishes a socket connection with server
* Client sends session initialization data which consists of a fixed length byte array declaring protocol version, message encoding version, etc. implemented by the client
* Server reads session initialization data and may close the socket connection
* Server starts message loop
* Client sends an initialization message which consists of sample rate, sample width, channel count, etc
* Server initializes Java Sound audio output and acknowledges the initialization message
* Client sends a start message
* Server requests a chunk of frames from client 
* Client renders frames and sends a frames message to server
* Server pushes the frames to the audio device
* Server requests next chunk of frames from client
* Client closes the connection by sending a close message

## Clients

[Common Lisp](https://github.com/Frechmatz/cl-java-sound-client)

