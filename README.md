# Artemis Google Proto Buffer Example

A simple example of how to use [Google Proto Buffer](https://developers.google.com/protocol-buffers/docs/javatutoria) to transmit messages via AMQP to Artemis broker.

This example has an embedded broker waiting for messages in the `mailbox` queue. The application itself produces and consumes from this queue using Google Proto Buffer. The message is an [`EmailProto`](https://github.com/ricardozanini/amq7-protobuf-sample/blob/master/src/main/java/amq7/samples/protobuf/EmailProto.java) object serialized in an array of bytes.

You can play with this repository and test this implementation by adding or removing fields from the object. If you're going to do that, don't forget to regen the proto class by using `compile_proto.sh` script:

```shell
protoc -I=src/main/resources/amq7/samples/protobuf/ --java_out=src/main/java src/main/resources/amq7/samples/protobuf/email_proto.proto
```

## How to use

1. Clone this repo: `git clone https://github.com/ricardozanini/amq7-protobuf-sample.git`
2. `cd amq7-protobuf-sample`
3. Run the Spring Boot application with `mvn spring-boot:run`

To produce messages, just visit [http://localhost:8080/produce](http://localhost:8080/produce).

## Notes

When doing `ctrl+c` to stop the application, one could see the following log message:

```log
[...]
2018-11-25 15:31:51 [Thread-3] WARN  o.a.activemq.artemis.core.server - AMQ222107: Cleared up resources for session 272bd105-f10a-11e8-91bc-44850020c5a1
2018-11-25 15:31:51 [Thread-3] WARN  o.a.activemq.artemis.core.server - AMQ222061: Client connection failed, clearing up resources for session 32d3d35a-f10a-11e8-91bc-44850020c5a1
2018-11-25 15:31:51 [Thread-3] WARN  o.a.activemq.artemis.core.server - AMQ222107: Cleared up resources for session 32d3d35a-f10a-11e8-91bc-44850020c5a1
2018-11-25 15:31:51 [Thread-3] INFO  o.a.activemq.artemis.core.server - AMQ221002: Apache ActiveMQ Artemis Message Broker version 2.6.3.redhat-00004 [2666fa62-f10a-11e8-91bc-44850020c5a1] stopped, uptime 58.224 seconds
2018-11-25 15:31:51 [Thread-3] WARN  o.m.pooled.jms.JmsPoolSession - Caught exception trying rollback() when putting session back into the pool, will invalidate. javax.jms.IllegalStateException: The Session is closed
javax.jms.IllegalStateException: The Session is closed
	at org.apache.qpid.jms.JmsSession.checkClosed(JmsSession.java:1041)
	at org.apache.qpid.jms.JmsSession.rollback(JmsSession.java:241)
	at org.messaginghub.pooled.jms.JmsPoolSession.close(JmsPoolSession.java:112)
	at org.springframework.jms.support.JmsUtils.closeSession(JmsUtils.java:109)
	at org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.clearResources(DefaultMessageListenerContainer.java:1241)
	at org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.access$100(DefaultMessageListenerContainer.java:1047)
	at org.springframework.jms.listener.DefaultMessageListenerContainer.doShutdown(DefaultMessageListenerContainer.java:593)
	at org.springframework.jms.listener.AbstractJmsListeningContainer.shutdown(AbstractJmsListeningContainer.java:243)
	at org.springframework.jms.listener.AbstractJmsListeningContainer.destroy(AbstractJmsListeningContainer.java:183)
	at org.springframework.jms.config.JmsListenerEndpointRegistry.destroy(JmsListenerEndpointRegistry.java:255)
	at org.springframework.beans.factory.support.DisposableBeanAdapter.destroy(DisposableBeanAdapter.java:256)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.destroyBean(DefaultSingletonBeanRegistry.java:571)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.destroySingleton(DefaultSingletonBeanRegistry.java:543)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.destroySingleton(DefaultListableBeanFactory.java:957)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.destroySingletons(DefaultSingletonBeanRegistry.java:504)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.destroySingletons(DefaultListableBeanFactory.java:964)
	at org.springframework.context.support.AbstractApplicationContext.destroyBeans(AbstractApplicationContext.java:1041)
	at org.springframework.context.support.AbstractApplicationContext.doClose(AbstractApplicationContext.java:1017)
	at org.springframework.context.support.AbstractApplicationContext$1.run(AbstractApplicationContext.java:937)
```

This means that the [JMS Pooled library](https://github.com/messaginghub/pooled-jms) invalidated the pooled session. This happens because the embedded server already stopped and the sessions/connections would die as well.
