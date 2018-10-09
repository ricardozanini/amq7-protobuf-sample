# Artemis Google Proto Buffer Example

A simple example of how to use [Google Proto Buffer](https://developers.google.com/protocol-buffers/docs/javatutoria) to transmit messages via AMQP to Artemis broker.

This example has an embedded broker waiting for messages in the `mailbox` queue. The application itself produces and consumes from this queue using Google Proto Buffer. The message is an `EmailProto` object serialized in an array of bytes.

You can play with this repository and test this implementation by adding or removing fields from the object. If you're going to do that, don't forget to regen the proto class by using `compile_proto.sh` script:

```shell
protoc -I=src/main/resources/amq7/samples/protobuf/ --java_out=src/main/java src/main/resources/amq7/samples/protobuf/email_proto.proto
```

To produce messages, just visit [http://localhost:8080/produce](http://localhost:8080/produce).