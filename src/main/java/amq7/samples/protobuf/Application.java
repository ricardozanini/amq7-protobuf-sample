package amq7.samples.protobuf;

import org.apache.activemq.artemis.core.config.impl.SecurityConfiguration;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.apache.activemq.artemis.spi.core.security.ActiveMQJAASSecurityManager;
import org.apache.activemq.artemis.spi.core.security.jaas.InVMLoginModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

/**
 * @see <a href="https://spring.io/guides/gs/messaging-jms/">Spring JMS Messaging Guide</a>
 */
@SpringBootApplication
@EnableJms
public class Application {

    private static final String GUEST_ROLE = "guest";

    public Application() {
    }

    public static void main(String[] args) {
        // Launch the application
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ActiveMQJAASSecurityManager securityManager(@Value("${amqphub.amqp10jms.username}") String user, @Value("${amqphub.amqp10jms.password}") String password) {
        final SecurityConfiguration configuration = new SecurityConfiguration();
        final ActiveMQJAASSecurityManager securityManager = new ActiveMQJAASSecurityManager(InVMLoginModule.class.getName(), configuration);
        configuration.addUser(user, password);
        configuration.addRole(user, GUEST_ROLE);
        configuration.setDefaultUser(user);

        return securityManager;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EmbeddedActiveMQ embeddedActiveMQ(ActiveMQJAASSecurityManager securityManager) {
        final EmbeddedActiveMQ embeddedActiveMQ = new EmbeddedActiveMQ();
        embeddedActiveMQ.setSecurityManager(securityManager);
        return embeddedActiveMQ;
    }
}
