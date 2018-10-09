package amq7.samples.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    public Receiver() {
    }

    @JmsListener(destination = "${amq.queue}")
    public void receiveMessage(byte[] emailProto) {
        try {
            final EmailProto.Email email = EmailProto.Email.parseFrom(emailProto);
            LOGGER.info("Received: {}", email.getAllFields());
        } catch (InvalidProtocolBufferException ex) {
            LOGGER.error("Not a protoBuf instance", ex);
        }
    }
}
