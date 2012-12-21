package config;

import java.io.IOException;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"controllers", "services"})
public class RabbitMqConfiguration {
	
    @Bean
    public ConnectionFactory connectionFactory() throws IOException {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("172.18.48.104");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");        
        return connectionFactory;
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate() throws IOException {
      return new RabbitTemplate(connectionFactory());
    }

}