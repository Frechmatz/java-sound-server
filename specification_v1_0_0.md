# Java-Sound-Server Specification

## Version 1.0.0

# Change-Log

Initial version

# The general flow

* Client establishes a socket connection with server
* Client sends session initialization data
* Server reads session initialization data and may close the connection
* Server starts message loop
* Client sends an `Init` message
* Server initializes Java Sound audio output and sends an `AckInit` message
* Client sends a `Start` message
* Server sends a `GetFrames` message
* Client sends a `Frames` message
* Server pushes the frames into the audio device. Blocks until device has accepted
* Server sends a `GetFrames` message
* Client sends a `Frames` or a `Stop` or a `Close` message

# Session initialization

After a connection has been established, the client must sent a session initialization
data block to let the server know about the message format and message exchange protocol
that will be used.

The session initialization data block has the following format:

* A sequence of 16 unsigned bytes
* Byte 0: Major version of specification implemented by client
* Byte 1: Minor version of specification implemented by client
* Byte 2: Patch version of specification implemented by client
* Bytes 3..15 must be 0

On acceptance the server sends back a zero-byte and starts the
message loop. Otherwise the server closes the connection.
    
# Message format

* The message format is `<start-of-message-marker>` `<message-payload>` `<end-of-message-marker>`
* The start marker consists of the byte sequence `1 2 3 4`
* The end marker consists of the byte sequence `4 3 2 1`
* Numerical properties are represented in big endian format

# Message property types

* `<MessageType>`: 2 Byte signed
* `<SampleRate>`: 4 Byte signed
* `<SampleWidth>`: 2 Byte signed
* `<ChannelCount>`: 2 Byte signed
* `<BufferSizeFrames>`: 4 Byte signed
* `<SampleDataLength>`: 4 Byte signed
* `<16BitSample>`: 2 Byte signed
* `<OmitAudioOutput>`: 2 Byte signed 

# Messages

## Init

Sent by client to initialize audio output. Server sends back an `AckInit` message or closes the connection.

* `<MessageType>`: 5
* `<SampleRate>`: Sample rate, for example 44100
* `<SampleWidth>`: Representation of a sample. 2 == `<16BitSample>`. Must be 2
* `<ChannelCount>`: Number of audio channels, for example 2
* `<BufferSizeFrames>`: Preferred buffer size of audio device. Might be overwritten by the server
* `<OmitAudioOutput>`: 1 == No audio output (can be used to measure throughput) 

## AckInit

Sent by server as response to an `Init` message to acknowledge that audio output has successfully been set up.

* `<MessageType>`: 9
* `<BufferSizeFrames>`: Buffer size of audio device that is used throughout the session

## GetFrames

Sent by server to request audio data. In general the client is supposed to send
back a `Frames` message providing an amount of samples according to `<BufferSizeFrames>`
as returned by `<AckInit>`.
The client may send back a less number of frames, because for example an underlying data source
has been exhausted.
The client may also send back a `Stop` message to pause audio output or a `Close` message
to close the connection.

* `<MessageType>`: 4

## Frames

Sent by client as response to a `GetFrames` message in order to provide audio data.

* `<MessageType>`: 3
* `<SampleDataLength>`: Length in bytes of following audio data
* `{ <16BitSample>{ChannelCount} }`: Sequence of frames. A frame consists of a sequence of samples, where for each channel one sample is provided

## Start

Sent by client to start audio output or to resume paused audio output. Server proceeds by sending `GetFrames` messages.

* `<MessageType>`: 7

## Stop

Sent by client to pause audio output. The server will no longer send `GetFrames` message.

* `<MessageType>`: 6

## Close

Sent by client to end the session. The server shuts down audio output and closes the connection.

* `<MessageType>`: 8

