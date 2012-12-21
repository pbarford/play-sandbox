package services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import beans.Subscription;

import com.rabbitmq.client.Channel;

import config.RabbitMqConfiguration;

@Component
public class SubscriptionService {

	private Map<Integer, Subscription> subscriptions = new HashMap<Integer, Subscription>();
	private static final String QUEUE_PREFIX = "queue:event:";
	@Autowired
	private RabbitMqConfiguration rabbitMqConfiguration;
	
	public Subscription subscribe(Integer eventId) throws IOException {
		System.out.println("SubscriptionService:subscribe");
		declareQueue(eventId);
		createListenerContainer(eventId);
		Subscription sub = new Subscription(createListenerContainer(eventId));
		sub.start();
		subscriptions.put(eventId, sub);
		return sub;
	}
	
	private void declareQueue(Integer eventId) throws IOException {
		System.out.println("SubscriptionService:queueDeclare");
		Channel channel = rabbitMqConfiguration.connectionFactory().createConnection().createChannel(true);
        channel.queueDeclare(QUEUE_PREFIX + eventId, false, false, false, null);
        channel.close();
	}
	
	private void deleteQueue(Integer eventId) throws IOException {
		System.out.println("SubscriptionService:deleteQueue");
		Channel channel = rabbitMqConfiguration.connectionFactory().createConnection().createChannel(true);
        channel.queueDelete(QUEUE_PREFIX + eventId);
        channel.close();
	}
	
	private SimpleMessageListenerContainer createListenerContainer(Integer eventId) throws IOException {
		System.out.println("SubscriptionService:createListenerContainer");
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitMqConfiguration.connectionFactory());
        container.setQueueNames(QUEUE_PREFIX + eventId);
        return container;
    }
	
	public void unsubscribe(Integer eventId) throws IOException {
		System.out.println("SubscriptionService:unsubscribe");
		if(subscriptions.containsKey(eventId)) {
			Subscription sub = subscriptions.remove(eventId);
			sub.shutdown();
			deleteQueue(eventId);
			sub = null;
		}
	}
}
