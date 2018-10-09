#!/bin/sh

protoc -I=src/main/resources/amq7/samples/protobuf/ --java_out=src/main/java src/main/resources/amq7/samples/protobuf/email_proto.proto
