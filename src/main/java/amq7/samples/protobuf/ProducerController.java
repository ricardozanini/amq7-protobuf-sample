package amq7.samples.protobuf;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${amq.queue}")
    private String queue;

    public ProducerController() {
    }

    @RequestMapping(method = RequestMethod.GET, path = "/produce", value = "/produce")
    public String produce(@RequestParam(value = "to", defaultValue = "test@test.com") String to, @RequestParam(value = "body", defaultValue = "test body") String body)
        throws JmsException, JsonProcessingException {
        EmailProto.Email.Builder email = EmailProto.Email.newBuilder();
        email.setTo(to);
        email.setBody(body);

        jmsTemplate.convertAndSend(queue, email.build().toByteArray());

        return "mail sent, check your logs";
    }
}
